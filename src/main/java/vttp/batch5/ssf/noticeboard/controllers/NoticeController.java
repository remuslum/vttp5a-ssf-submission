package vttp.batch5.ssf.noticeboard.controllers;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.validation.Valid;
import vttp.batch5.ssf.noticeboard.components.CheckHealth;
import vttp.batch5.ssf.noticeboard.models.Notice;
import vttp.batch5.ssf.noticeboard.services.NoticeService;


// Use this class to write your request handlers
@Controller
@RequestMapping
public class NoticeController {
    @Autowired
    NoticeService noticeService;

    @Autowired
    CheckHealth checkHealth;

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
            // Check if categories are empty
            if(notice.getCategories().isEmpty()){
                FieldError error = new FieldError("notice", "categories", "There must be at least one category selected");
                bindingResult.addError(error);
                mav.setViewName("notice");
            // Check if postDate is in the past
            } else if (ChronoUnit.DAYS.between(LocalDate.now(), notice.getPostDate()) < 0){
                FieldError error = new FieldError("notice", "postDate", "Post Date cannot be in the past");
                bindingResult.addError(error);
                mav.setViewName("notice");
            // Check if request sent to API was successful or not
            } else {
                JsonObject jsonObject = noticeService.postToNoticeServer(notice);
                // Unsuccessful requests has the key message in the response
                if(jsonObject.keySet().contains("message")){
                    mav.addObject("error", jsonObject.getString("message"));
                    mav.setViewName("view3");
                } else {
                    mav.addObject("id", jsonObject.getString("id"));
                    mav.setViewName("view2");
                }  
            }
        }
        return mav;
    }

    @GetMapping(path = "/status", produces="application/json")
    @ResponseBody
    public ResponseEntity<String> checkHealth() {
        JsonObject jsonObject = Json.createObjectBuilder().build();
        if(checkHealth.isAppHealthy()){
            return ResponseEntity.status(200).body(jsonObject.toString());
        } else {
            return ResponseEntity.status(503).body(jsonObject.toString());
        }
    }
}
    
