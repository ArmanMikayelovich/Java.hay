package hay.java.controller.site;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

public interface HomeController {
    ModelAndView getHomePage(HttpServletResponse response);
}
