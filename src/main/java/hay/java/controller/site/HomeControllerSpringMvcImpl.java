package hay.java.controller.site;

import hay.java.controller.site.interfaces.HomeController;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = {"/home", "/index", ""})
public class HomeControllerSpringMvcImpl implements HomeController {


    @Override
    @RequestMapping(value = "",method = RequestMethod.GET,produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getHomePage(HttpServletResponse response) {
        String viewName;
        ModelAndView model = new ModelAndView("homePage");
        return model;
    }

    @RequestMapping(value = "/topics", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView testTopicPage(HttpServletResponse response) {
        String viewName;
        ModelAndView model = new ModelAndView("exams/topics");
        return model;
    }

    @RequestMapping(value = "/chapters", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView testChapterPage(HttpServletResponse response) {
        String viewName;
        ModelAndView model = new ModelAndView("exams/chapters");
        return model;
    }

    @RequestMapping(value = "/questions", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView testQuestionPage(HttpServletResponse response) {
        String viewName;
        ModelAndView model = new ModelAndView("exams/questions");
        return model;
    }

    @RequestMapping(value = "/answers", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView testAnswerPage(HttpServletResponse response) {
        String viewName;
        ModelAndView model = new ModelAndView("exams/answers");
        return model;
    }
}
