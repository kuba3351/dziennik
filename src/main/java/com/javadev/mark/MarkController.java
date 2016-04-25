package com.javadev.mark;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by kuba3 on 25.04.2016.
 */
@RestController
public class MarkController {
    @RequestMapping("/")
    public String welcome()
    {
        return "test";
    }
}
