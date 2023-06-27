package com.mc.blogadmin.handler;

import com.mc.blogadmin.handler.exp.IdTypeException;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({BindException.class})
    public String handlerBindException(BindException bindException, Model model){
        BindingResult bindingResult = bindException.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        model.addAttribute("errors",fieldErrors);
        /*FieldError fieldError = fieldErrors.get(0);
        fieldError.getField();
        fieldError.getDefaultMessage();*/
        return "/blog/error/bind";
    }

    @ExceptionHandler({IdTypeException.class})
    public String handlerIdTypeException(IdTypeException idTypeException,Model model){
        model.addAttribute("msg",idTypeException.getMessage());
        return "/blog/error/error";
    }

    @ExceptionHandler({Exception.class})
    public String handlerDefaultException(Exception exception, Model model){
        model.addAttribute("msg","请稍后重试！！！");
        return "/blog/error/error";
    }

}
