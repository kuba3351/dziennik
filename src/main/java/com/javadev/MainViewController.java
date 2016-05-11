package com.javadev;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by kuba3 on 09.05.2016.
 */
@Controller
public class MainViewController {
    @RequestMapping(value="/", method = RequestMethod.GET)
    public String hello()
    {
        return "index";
    }
}
