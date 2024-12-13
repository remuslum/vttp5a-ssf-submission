package vttp.batch5.ssf.noticeboard.controllers;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;
import vttp.batch5.ssf.noticeboard.components.JSONParser;
import vttp.batch5.ssf.noticeboard.models.Notice;

// Use this class to write your request handlers
@Controller
@RequestMapping
public class NoticeController {
    @Autowired
    JSONParser jsonParser;

    @GetMapping
    public ModelAndView getLandingPage(){
        ModelAndView mav = new ModelAndView();
        mav.addObject("notice", new Notice());
        mav.setViewName("notice");
        return mav;
    }

    @PostMapping(path = "/notice")
    public ModelAndView submitPosterDetails(@Valid @ModelAttribute Notice notice, BindingResult bindingResult){
        ModelAndView mav = new ModelAndView();
        if(bindingResult.hasErrors()){
            mav.setViewName("notice");
        } else {
            //Check if categories are empty
            if(notice.getCategories().isEmpty()){
                FieldError error = new FieldError("notice", "categories", "There must be at least one category selected");
                bindingResult.addError(error);
                mav.setViewName("notice");
            //Check if postDate is in the past
            } else if (ChronoUnit.DAYS.between(LocalDate.now(), notice.getPostDate()) < 0){
                FieldError error = new FieldError("notice", "postDate", "Post Date cannot be in the past");
                bindingResult.addError(error);
                mav.setViewName("notice");
            } else {
                System.out.println(jsonParser.parseNoticeToJSON(notice));
                mav.setViewName("view2");
            }
        }
        return mav;
    }
}
