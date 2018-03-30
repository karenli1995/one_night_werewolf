package main.java.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import main.java.form.FormData;
import main.java.networking.GameClient;

@RestController
public class MVCController {
    private static final String myServerAddress = "localhost";

    @Autowired
    private BackendController backEndCtrl;
    
    //TODO
    @RequestMapping(value = "")
    public ModelAndView homepage() {
        return null;
    }

    @RequestMapping(value = "/startNewGame", method = RequestMethod.GET)
    public ModelAndView startPage() {
        ModelAndView modelAndView = new ModelAndView("index", "formData", new FormData());
        return modelAndView;
    }

    @RequestMapping(value = "/formSubmitted", method = RequestMethod.POST)
    public ModelAndView formSubmitted(@ModelAttribute FormData form) {
        ModelAndView modelAndView = new ModelAndView();
        form.getNames();
        form.getRoles();

        String[] names = form.getNames().split(" ");
        String[] roles = form.getRoles().split(" ");
        List<Integer> initRoles= new ArrayList<Integer>();
        for(String num : roles) {
            System.out.println(Integer.parseInt(num));
            initRoles.add(Integer.parseInt(num));
        }

        backEndCtrl.passDataForSetUp(names, roles);
//        try {
//            backEndCtrl.startGameServer();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        modelAndView.setViewName("done");
        return modelAndView;
    }
    
    @RequestMapping(value = "/playGame")
    public ModelAndView playGame() {
        ModelAndView modelAndView = new ModelAndView();
        
        GameClient client = null;
        try {
            client = new GameClient(myServerAddress);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            client.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        modelAndView.setViewName("game");
        return modelAndView;
    }
}
