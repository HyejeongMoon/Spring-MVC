package hello.servlet.basic.response;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "responseHeaderServlet", urlPatterns = "/response-header")
public class ResponseHeaderServlet extends HttpServlet {
    
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        //[status-line]
        response.setStatus(HttpServletResponse.SC_OK); //200

        //[response-headers]
        response.setHeader("Content-Type", "text/plain;charset=utf-8");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("my-header","hello");

        //[Header 편의 메서드] 
        //content(response); 
        //cookie(response); 
        //redirect(response);

        //[message body]
        PrintWriter writer = response.getWriter();
        writer.print("ok"); //이 길이를 자동으로 계산 -> Content-Length : 2 

    }


    private void content(HttpServletResponse response) {

        //Content-Type: text/plain;charset=utf-8
        //Content-Length: 2
        //response.setHeader("Content-Type", "text/plain;charset=utf-8"); 
        response.setContentType("text/plain"); 
        response.setCharacterEncoding("utf-8"); 
        //response.setContentLength(5); //(임의로 설정 가능, 생략시 자동 생성)
    }

    private void cookie(HttpServletResponse response) {

        //Set-Cookie: myCookie=good; Max-Age=600; 
        //response.setHeader("Set-Cookie", "myCookie=good; Max-Age=600"); //아래의 쿠키 객체를 사용하면 이것과 같은 동작
        Cookie cookie = new Cookie("myCookie", "good"); 
        cookie.setMaxAge(600); //600초 유효
        response.addCookie(cookie);
    }


    private void redirect(HttpServletResponse response) throws IOException {

        //Status Code 302
        //Location: /basic/hello-form.html
        //response.setStatus(HttpServletResponse.SC_FOUND); //302
        //response.setHeader("Location", "/basic/hello-form.html");
        response.sendRedirect("/basic/hello-form.html");

    }
        
         
}
