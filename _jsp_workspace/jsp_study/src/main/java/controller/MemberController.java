package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.MemberVO;
import service.MemberService;
import service.MemberServiceImpl;

@WebServlet("/memb/*")
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    //로그 객체
	private static final Logger log = LoggerFactory.getLogger(MemberController.class);
	//rdp
	private RequestDispatcher rdp;
	//destPage
	private String destPage;
	//isOk
	private int isOk;
	
	//service
	private MemberService msv;
    
    public MemberController() {
        msv = new MemberServiceImpl();
        // TODO Auto-generated constructor stub
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String uri = request.getRequestURI();
		String path = uri.substring(uri.lastIndexOf("/") + 1);
		
		log.info(">>> path >> "+ path);
		
		switch(path) {
		case "join" : 
			//index의 /member/join 경로...
			destPage = "/member/join.jsp";
			break;
		case "register" : 
			try {
				String id = request.getParameter("id");
				String pwd = request.getParameter("pwd");
				String email = request.getParameter("email");
				int age = Integer.parseInt(request.getParameter("age"));
				
				MemberVO mvo = new MemberVO(id, pwd, email, age);
				log.info(">>> join check 1 >>> {}", mvo);
				
				isOk = msv.register(mvo);
				log.info("join >> {}", (isOk > 0) ? "OK" : "Fail");
				
				destPage = "/index.jsp";
						
			} catch (Exception e) {
				// TODO: handle exception
				log.info("register error");
				e.printStackTrace();
			}
			break;
		case "login" : 
			try {
				//id, pwd를 jsp에서 전송
				//id, pwd를 객체화 하여 db로 전송
				//같은 이름의 id/pwd가 있다면 로그인 (세션 객체에 값을 저장)
				//session : 모든 jsp에서 공유되는 객체
				//id가 없으면, alert 창을 이용하여 로그인 정보가 없습니다. 메시지 띄우기.
				String id = request.getParameter("id");
				String pwd = request.getParameter("pwd");
				
				MemberVO mvo = new MemberVO(id, pwd);
				
				MemberVO loginMvo = msv.login(mvo); // id와 pwd가 일치하는 데이터의 객체를 리턴
				log.info(">>> login mvo >>> {}"+ loginMvo);
				
				//로그인 객체가 있음을 의미 만약 로그인 객체가 없다면 loginMvo = null
				//가져온 로그인 객체를 세션에 저장
				if(loginMvo != null) {
					//세션 객체 가져오기
					//연결된 세션 객체가 있다면 기존 객체 가져오기, 없으면 생성
					//세션은 싱글톤임 => 있으면 가져오고 없으면 생성
					HttpSession ses = request.getSession();
					ses.setAttribute("ses", loginMvo);
					ses.setMaxInactiveInterval(10*60); //로그인 유지시간(초단위로 설정)
				}else {
					//로그인 객체가 없다면
					request.setAttribute("msg_login", -1);
				}
				
				destPage = "/index.jsp";
				
			} catch (Exception e) {
				// TODO: handle exception
				log.info(">>> login error");
				e.printStackTrace();
			}
			break;
		case "logout" : 
			try {
				//세션에 값이 있다면 해당 세션 연결 끊기
				HttpSession ses = request.getSession();
				
				//lastLogin 정보 update
				//ses에서 mvo 객체로 가져오기.
				MemberVO mvo = (MemberVO) ses.getAttribute("ses");
				log.info("ses에서 추출한 mvo >> {}", mvo);
				
				//lastLogin update
				isOk = msv.lastLogin(mvo.getId());
				log.info("lastLogin >>> {} ", isOk > 0 ? "OK" : "Fail");
				ses.invalidate(); //세션 무효화(세션 끊기)
				destPage = "/index.jsp";					
		
			} catch (Exception e) {
				// TODO: handle exception
				log.info(">>> logout error");
				e.printStackTrace();
			}

			break;
		default : 
			break;
		}
		
		//목적지 주소 값 설정
		rdp = request.getRequestDispatcher(destPage);
		//목적지 주소로 전송.(정보 싣어서 보내기)
		rdp.forward(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		service(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		service(request, response);
	}

}
