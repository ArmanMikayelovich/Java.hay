package hay.java.controller.site.interfaces;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

public interface HomeController {
    ModelAndView getHomePage(HttpServletResponse response);
}
