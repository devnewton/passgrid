package im.bci.passgrid.controllers;

import im.bci.passgrid.frontend.ErrorMV;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 *
 * @author devnewton
 */
@Controller
public class CustomErrorController implements ErrorController {
    
    @Autowired
    private ErrorAttributes errorAttributes;

    @RequestMapping(value = "/error")
    public String error(Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("error", buildErrorMV(request, response));
        return "error";
    }

    public ErrorMV buildErrorMV(HttpServletRequest request, HttpServletResponse response) {
        return new ErrorMV(response.getStatus(), getErrorAttributes(request, true));
    }

    private Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        return errorAttributes.getErrorAttributes(requestAttributes, includeStackTrace);
    }

    @Override
    public String getErrorPath() {
        return "error";
    }

}
