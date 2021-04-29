package na.spring.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.log4j.Log4j2;
import na.spring.domain.SampleDTO;
import na.spring.domain.SampleDTOList;
import na.spring.domain.TodoDTO;

@Controller
@RequestMapping("/sample/")
@Log4j2
public class SampleController {

    @InitBinder
    public void InitBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(dateFormat, false));
    }

    @RequestMapping("")
    public void basic() {
        log.info("basic......................................");
    }

    @RequestMapping(value = "/basic", method = { RequestMethod.GET, RequestMethod.POST })
    public void basicGet() {
        log.info("basic get......................");
    }

    @GetMapping("/basicOnlyGet")
    public void basicGet2() {
        log.info("basic get only get................");
    }

    @GetMapping("/ex01")
    public String ex01(SampleDTO dto) {
        log.info("" + dto);
        return "ex01";
    }

    @GetMapping("/ex02")
    public String ex02(@RequestParam("name") String name, @RequestParam("age") int age) {
        log.info("name: " + name);
        log.info("age: " + age);
        return "ex02";
    }

    @GetMapping("/ex02List")
    public String ex02List(@RequestParam("ids") ArrayList<String> ids) {
        log.info("ids: " + ids);
        return "ex02List";
    }

    @GetMapping("/ex02Bean")
    public String ex02Bean(SampleDTOList list) {
        log.info("list dtos: " + list);
        return "ex02Bean";
    }

    @GetMapping("/ex03")
    public String ex03(TodoDTO todo) {
        log.info("todo: " + todo);
        return "ex03";
    }

    @RequestMapping("/home")
    public ModelAndView test() throws Exception {
        ModelAndView mav = new ModelAndView("home");
        mav.addObject("name", "gg");
        List<String> testList = new ArrayList<String>();
        testList.add("a");
        testList.add("b");
        testList.add("c");
        mav.addObject("list", testList);
        return mav;
    }

}
