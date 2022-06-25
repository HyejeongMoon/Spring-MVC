package hello.servlet.web.frontcontroller.v3;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;

@WebServlet(name="frontControllerServletV3", urlPatterns="/front-controller/v3/*")
public class FrontControllerservletV3 extends HttpServlet{


    private Map<String, ControllerV3> controllerMap = new HashMap<>();

    public FrontControllerservletV3() {

        controllerMap.put("/front-controller/v3/members/new-form", new MemberFormControllerV3());
        controllerMap.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
        controllerMap.put("/front-controller/v3/members", new MemberListControllerV3());
        
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV3.service");

        String requestURI = request.getRequestURI();

        ControllerV3 controller = controllerMap.get(requestURI);
        if(controller == null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // V3 : paramMap을 return
        // 1. getParameterNames로 모든 parameter이름을 가져온다
        // 2. 가져온 파라미터 이름으로 모든 값을 가져와서 paramMap에 담는다
        // Map<String,String> paramMap = new HashMap<>();
  
        // request.getParameterNames().asIterator()
        // .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));

        //위의 로직을 메소드롤 뽑아 전체적인 코드의 레벨을 맞춰준다.
        Map<String, String> paramMap = createParamMap(request);
        

        // V2
        // MyView view = controller.process(request, response);
        // view.render(request, response);

        // V3
        ModelView mv = controller.process(paramMap);

        String viewName = mv.getViewName(); // 논리이름
        //MyView view = new MyView("/WEB-INF/views/" + viewName + ".jsp");
        MyView view = viewResolver(viewName);
        
        view.render(mv.getModel(), request, response);

    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String,String> paramMap = new HashMap<>();
  
        request.getParameterNames().asIterator()
        .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }
    
}
