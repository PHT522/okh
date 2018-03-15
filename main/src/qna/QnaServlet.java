package qna;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class QnaServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		myFunc(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		myFunc(req, resp);
	}
	
	protected void dispatch(String urls, 
			HttpServletRequest req, HttpServletResponse resp)
					throws ServletException, IOException {
		
		RequestDispatcher _dispatch = req.getRequestDispatcher(urls);
		_dispatch.forward(req, resp);		
	}

	protected void myFunc(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.setCharacterEncoding("utf-8");
		resp.setContentType("html/text; charset=utf-8");
		
		String command =req.getParameter("command");
		
		QnaServiceImpl service = QnaService.getInstance();
		
		if(command.equals("writeQna")) {
			System.out.println("여기는 writeQna입니다");
		
			QnaDto dto = new QnaDto();
			
			dto.setId(req.getParameter("iD"));
			dto.setTitle(req.getParameter("tItle"));
			dto.setTag(req.getParameter("tAg"));
			dto.setContent(req.getParameter("cOntent"));
			
		//	qnaBbsDaoImpl dao = qnaBbsDao.getInstance();
		//	dao.writeQnaBbs(dto);
			
			service.writeQnaBbs(dto);			
			
			//RequestDispatcher rd = req.getRequestDispatcher("/qnaServlet?command=listQna");						
            //rd.forward(req, resp);
			resp.sendRedirect("qnaServlet?command=listQna");
			
		}else if(command.equals("listQna")) {
			System.out.println("여기는 listQna");
			
			
			
		//	qnaBbsDaoImpl dao = qnaBbsDao.getInstance();
		//	dao.getQnaList();			
			
			service.getQnaList();
			
			RequestDispatcher rd = req.getRequestDispatcher("qnabbslist.jsp");

            rd.forward(req, resp);
			
		}else if(command.equals("qnaBbsDetail")) {
			System.out.println("여기는 qnadetail");
			String Sseq= req.getParameter("seq");
			int seq = Integer.parseInt(Sseq);	
			
			QnaDto dto = service.getBbs(seq);
						
			/*req.getSession().setAttribute("detailDto", dto);
			RequestDispatcher rd = req.getRequestDispatcher("qnabbsdetail.jsp");*/
			
			String action = req.getParameter("action").trim();
			req.getSession().setAttribute("detailDto", dto);
			RequestDispatcher rd = null;
			if(action.equals("update")) {
				rd = req.getRequestDispatcher("qnabbsupdate.jsp");
				System.out.println("update="+rd);
			}else if(action.equals("detail")){				
				rd = req.getRequestDispatcher("qnabbsdetail.jsp");	
				System.out.println("detail="+rd);
			}
			
			rd.forward(req, resp);
			
			
			
		
		}else if(command.equals("updateQnaAf")) {
			System.out.println("여기는 qnaUpdateAf Servlet");
			String Sseq= req.getParameter("seq");
			int seq = Integer.parseInt(Sseq);	
						
			QnaDto dto = new QnaDto();
			
			dto.setSeq(seq);
			dto.setTitle(req.getParameter("tItle").trim());			
			dto.setContent(req.getParameter("cOntent").trim());
			dto.setTag(req.getParameter("tAg").trim());		
			boolean isS = service.qnaupdate(dto);			
			System.out.println("isS is " +isS);
			
			if(isS) {
				req.getSession().setAttribute("txtAlert", "수정 되었습니다.");
			}else {
				req.getSession().setAttribute("txtAlert", "수정이 안되었습니다.");
			}
			
			/*RequestDispatcher rd = req.getRequestDispatcher("qnabbslist.jsp");
			rd.forward(req, resp);*/
			resp.sendRedirect("qnaServlet?command=listQna");
			
		}else if(command.equals("updateQna")) {
			//detail과 합침
		}else if(command.equals("writeAnswer")) {
			//답변 내용 적용
			QnaDto dto = new QnaDto();
			String id = req.getParameter("iD").trim();
			String title = req.getParameter("tItle").trim();			
			String Sanswercount = req.getParameter("aNswerCount").trim();
			int answercount = Integer.parseInt(Sanswercount);
			int seq = Integer.parseInt(req.getParameter("seq").trim());
			String tag = "tag";
			
			dto.setId(id);
			dto.setTitle(title);			
			dto.setAnswercount(answercount);
			dto.setSeq(seq);
			dto.setTag(tag);
			boolean isS = service.answer(seq, dto);
			resp.sendRedirect("qnaServlet?command=listQna");
			//resp.sendRedirect("qnabbswrite.jsp");
			
		}
		
		
		
	}
	
	
	
	
	
}
