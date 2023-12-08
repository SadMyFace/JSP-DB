package controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.BoardVO;
import domain.PagingVO;
import handler.FileRemoveHandler;
import handler.PagingHandler;
import net.coobird.thumbnailator.Thumbnails;
import service.BoardService;
import service.BoardServiceImpl;
import service.CommentService;
import service.CommentServiceImpl;

/**
 * Servlet implementation class BoardController
 */
@WebServlet("/brd/*")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//로그 기록 객체 생성
	private static final Logger log = 
			LoggerFactory.getLogger(BoardController.class);
	// jsp에서 받은 요청을 처리, 처리 결과를 다른 jsp로 보내는 역할
	private RequestDispatcher rdp;
	private String destPage; // 목적지 주소를 저장하는 변수
	private int isOk; // DB 구문 체크 값 저장변수
	private String savePath; // 파일 저장 경로
	
	// controller <-> service
	private BoardService bsv; // interface로 생성
	private CommentService csv;
	
	
       
    public BoardController() {
        // 생성자
    	bsv = new BoardServiceImpl(); // class로 생성 bsv를 구현할 객체
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 실제 처리 메서드
		log.info("필요한 로그 띄우기 가능.");
		//매개변수의 객체의 인코딩 설정
		request.setCharacterEncoding("utf-8"); // 요청
		response.setCharacterEncoding("utf-8"); // 응답
		response.setContentType("text/html; charset=UTF-8");
		
		String uri = request.getRequestURI(); // jsp에서 오는 요청 주소를 받는 설정
		System.out.println("sysout을 통한 로그 >> " + uri);
		log.info("log객체를 통한 로그 >> " + uri);
		String path = uri.substring(uri.lastIndexOf("/") + 1); // register
		
		log.info("실 요청 경로 >>> " + path);
		
		switch(path) {
		case "register" : 
			destPage = "/board/register.jsp";
			break;
		case "insert" : 
			try {
				//파일을 업로드할 물리적인 경로 설정
				//웹 어플리케이션의 컨텍스트 루트를 기준으로 지정된 경로의 실제 파일 시스템 경로를 반환
				savePath = getServletContext().getRealPath("/_fileUpload");
				File fileDir = new File(savePath);
				log.info("저장 위치 >>> {} " + savePath);
				
				DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
				fileItemFactory.setRepository(fileDir); //저장할 위치를 file 객체로 지정
				fileItemFactory.setSizeThreshold(1024*1024*3); //파일 저장을 위한 임시 메모리 용량 설정 : byte 단위
				
				//미리 Board 객체 설정
				BoardVO bvo = new BoardVO();
				
				//multipart/for-data 형식으로 넘어온 request 객체를 다루기 쉽게 변환해주는 역할
				ServletFileUpload fileUpload = new ServletFileUpload(fileItemFactory);
				
				List<FileItem> itemList = fileUpload.parseRequest(request);
				
				for(FileItem item : itemList) {
					switch (item.getFieldName()) {
					case "title" :
						bvo.setTitle(item.getString("utf-8"));
						break;
					case "writer" : 
						bvo.setWriter(item.getString("utf-8"));
						break;
					case "content" : 
						bvo.setContent(item.getString("utf-8"));
						break;
					case "image_file" : 
						//이미지 있는지 체크
						if(item.getSize() > 0) { //데이터의 크기를 바이트 단위로 리턴 / 크기가 0보다 큰지 체크
							String fileName = item.getName() // .getName()은 경로를 포함한 이름을 리턴    ~~~~~ / dog.jpg
									.substring(item.getName().lastIndexOf(File.separator) + 1); // 이름만 분리 
							//File.separator : 파일 경로 기호를 저장.
							//시스템의 시간을 이용하여 파일을 구분 / 시간_dog.jpg
							fileName = System.currentTimeMillis() + "_" + fileName;
							File uploadFilePath = new File(fileDir + File.separator + fileName);
							log.info("uploadFilePath >>> {} " + uploadFilePath.toString());
							
							//저장
							try {
								item.write(uploadFilePath); //자바 객체를 디스크에 쓰기
								bvo.setImageFile(fileName); //bvo에 저장할 값
								
								//썸네일 작업 : 리스트 페이지에서 트래픽 과다사용 방지
								Thumbnails.of(uploadFilePath)
								.size(75, 75)
								.toFile(new File(fileDir + File.separator + "_th_" + fileName));
								
							} catch (Exception e) {
								// TODO: handle exception
								log.info(">>> file writer on disk error");
								e.printStackTrace();
							}
						}
						break;
					default:
						break;
					}
				}
				
				//만들어진 bvo를 db에 저장
				isOk = bsv.register(bvo);
				log.info("board register >>>> {} ", isOk > 0 ? "OK" : "Fail");
				
				//목적지 주소
				destPage = "/index.jsp";
				
				
				//fileUpload 없는 경우
//				//jsp에서 데이터를 입력 후 => controller로 전송
//				//DB에 등록한 후 => index.jsp로 이동
//				//jsp에서 가져온 title, writer, content를 꺼내오기.
//				String title = request.getParameter("title");
//				String writer = request.getParameter("writer");
//				String content = request.getParameter("content");
//				log.info(">>>> insert check 1");
//				
//				BoardVO bvo = new BoardVO(title, writer, content);
//				log.info("insert bvo >>> {} " + bvo);
//				
//				//만들어진 bvo를 db에 저장
//				isOk = bsv.register(bvo);
//				log.info("board register >>>> {} ", isOk > 0 ? "OK" : "Fail");
//				
//				//목적지 주소
//				destPage = "/index.jsp";
				
			} catch (Exception e) {
				// TODO: handle exception
				log.info("insert Error");
				e.printStackTrace();
			}
			break;
		case "list" : 
			try {
				//index에서 list 버튼을 클릭하면
				//컨트롤러에서 db로 전체 리스트 요청
				//전체 리스트를 가지고 list.jsp에 뿌리기
				log.info("list check 1");
				PagingVO pgvo = new PagingVO(); // 1/ 10 /0
				
				if(request.getParameter("pageNo") != null) {
					int pageNo = Integer.parseInt(request.getParameter("pageNo"));
					int qty = Integer.parseInt(request.getParameter("qty"));
					String type = request.getParameter("type");
					String keyword = request.getParameter("keyword");
					log.info(">>> pageNo / qty " + pageNo + " / " + qty + " / " + type + " / " + keyword);
					pgvo = new PagingVO(pageNo, qty, type, keyword);
				}
				
				List<BoardVO> list = bsv.getList(pgvo);
				log.info("list >>>> {} " + list);
				
				//검색한 값의 게시글 카운트	
				log.info(">>> totalCount check 1");
				int totalCount = bsv.getCount(pgvo); //db에서 전체 게시글 수 가져오기
				log.info("totalCount >>> {} " + totalCount);
				
				PagingHandler ph = new PagingHandler(pgvo, totalCount);
				
				//list를 jsp로 전송
				//검색어를 반영한 리스트
				request.setAttribute("list", list);
				request.setAttribute("ph", ph);
				destPage = "/board/list.jsp";
			} catch (Exception e) {
				// TODO: handle exception
				log.info("list Error");
				e.printStackTrace();
			}
			break;
		case "detail" :
			try {
				//jsp에서 보낸 bno를 받아서
				//해당 번호의 전체 값을 조회하여 detail.jsp에 뿌리기
				int bno = Integer.parseInt(request.getParameter("bno"));
				log.info("detail check 1");
				
				BoardVO bvo = bsv.getDetail(bno);
				log.info("detail >>>> {}" + bvo);
				request.setAttribute("bvo", bvo);
				destPage = "/board/detail.jsp";
			} catch (Exception e) {
				// TODO: handle exception
				log.info("detail Error");
				e.printStackTrace();
			}
			break;
		case "modify" : 
			try {
				//수정할 데이터의 bno를 받아서 수정 페이지로 보내서
				//modify.jsp를 띄우기
				int bno = Integer.parseInt(request.getParameter("bno"));
				
				BoardVO bvo = bsv.getDetail(bno);
				
				request.setAttribute("bvo", bvo);
				destPage = "/board/modify.jsp";
			} catch (Exception e) {
				// TODO: handle exception
				log.info("modify Error");
				e.printStackTrace();
			}
			break;
		case "remove" : 
			try {
				//파라미터로 받은 bno, title, content 데이터를
				//DB에 수정하여 넣고, list로 이동
				int bno = Integer.parseInt(request.getParameter("bno"));
				log.info("remove check 1");
				//댓글, 파일도 같이 삭제
				savePath = getServletContext().getRealPath("/_fileUpload");
				
				String file_name = bsv.findFile(bno);
				if(file_name.length() > 0) {
					FileRemoveHandler fh = new FileRemoveHandler();
					fh.deleteFile(file_name, savePath);
				}
				csv = new CommentServiceImpl();
				isOk = csv.removeComment(bno);
				log.info("comment remove >>>> {} ", isOk > 0 ? "OK" : "Fail");
				
				isOk = bsv.remove(bno);
				destPage = "list";
			} catch (Exception e) {
				// TODO: handle exception
				log.info("delete Error");
				e.printStackTrace();
			}
			break;
		case "edit" : 
			try {
				savePath = getServletContext().getRealPath("/_fileUpload");
				File fileDir = new File(savePath);
				
				DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
				fileItemFactory.setRepository(fileDir);
				fileItemFactory.setSizeThreshold(1024 * 1024 * 3);
				
				BoardVO bvo = new BoardVO();
				
				ServletFileUpload fileUpload = new ServletFileUpload(fileItemFactory);
				
				List<FileItem> itemList = fileUpload.parseRequest(request);
				
				String old_file = null;
				
				for(FileItem item : itemList) {
					switch(item.getFieldName()) {
					case "bno" : 
						bvo.setBno(Integer.parseInt(item.getString("utf-8")));
						break;
					case "title" :
						bvo.setTitle(item.getString("utf-8"));
						break;
					case "content" :
						bvo.setContent(item.getString("utf-8"));
						break;
					case "imgae_file" : 
						//이전 그림 파일의 보관용
						old_file = item.getString("utf-8");
						break;
					case "new_file" : 
						//새로운 파일은 등록이 될 수도 있고, 안될 수도 있음.
						if(item.getSize() > 0) {
							//새로운 등록 파일이 있다면...
							if(old_file != null) {
								//old_file 삭제 작업
								//파일 삭제 핸들러를 통해서 파일 삭제 작업 진행
								FileRemoveHandler fh = new FileRemoveHandler();
								isOk = fh.deleteFile(old_file, savePath);
								log.info(">>> oldFile Delete >>> {} " + ((isOk > 0) ? "OK" : "Fail"));
							}
							//새로운 파일에 대한 객체 작업
							String fileName = item
									.getName()
									.substring(item.getName().lastIndexOf(File.separator) + 1);
							log.info("new file Name >>> {} " + fileName);
							
							fileName = System.currentTimeMillis() + "_" + fileName;
							File uploadFilePath = new File(fileDir + File.separator + fileName);
							
							try {
								item.write(uploadFilePath);
								bvo.setImageFile(fileName);
								
								//썸네일 작업
								Thumbnails.of(uploadFilePath)
								.size(75, 75)
								.toFile(new File(fileDir + File.separator + "_th_" + fileName));
							} catch (Exception e) {
								// TODO: handle exception
								log.info("File Update Error");
								e.printStackTrace();
							}
						}else {
							//기존 파일은 있지만, 새로운 이미지 파일이 없다면...
							//기존 파일을 담지 않으면 null로 바뀜
							bvo.setImageFile(old_file); // 기본 파일을 bvo에 담기
						}
						break;
					default : 
						break;
					}
				}
				
				isOk = bsv.modify(bvo);
				log.info("edit >> {} ", isOk > 0 ? "OK" : "Fail");
				destPage = "list"; //내부 list case로 이동
				
//				int bno = Integer.parseInt(request.getParameter("bno"));
//				String title = request.getParameter("title");;
//				String content = request.getParameter("content");
//				
//				BoardVO bvo = new BoardVO(bno, title, content);
//				log.info("edit check 1");
//				log.info("edit >>> {} " + bvo);
//				
//				isOk = bsv.modify(bvo);
//				log.info("edit >> {} ", isOk > 0 ? "OK" : "Fail");
//				destPage = "list"; //내부 list case로 이동
			} catch (Exception e) {
				// TODO: handle exception
				log.info("edit Error");
				e.printStackTrace();
			}
		}
		
		// 목적지 주소로 데이터를 전달(RequestDispatcher)
		
		rdp = request.getRequestDispatcher(destPage);
		rdp.forward(request, response); // 요청에 필요한 객체를 가지고 destPage 경로로 전송
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// get으로 오는 요청 처리
		service(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// post로 오는 요청 처리
		service(request, response);
	}

}
