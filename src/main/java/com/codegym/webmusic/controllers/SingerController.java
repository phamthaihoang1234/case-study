package com.codegym.webmusic.controllers;


import com.codegym.webmusic.model.Singer;
import com.codegym.webmusic.service.singer.SingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/singers")
public class SingerController {

    @Autowired
    SingerService singerService;

    @Autowired
    Environment env;


    @GetMapping("/singers")
    public ModelAndView listBlog(){
        Iterable<Singer> singers = singerService.findAll();
        ModelAndView modelAndView = new ModelAndView("/singer/list");
        modelAndView.addObject("singers", singers);
        return modelAndView;
    }
    @GetMapping("/create")
    public ModelAndView showCreateSinger() {
        ModelAndView modelAndView = new ModelAndView("/singer/create");
        modelAndView.addObject("singer", new Singer());
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView saveProduct(@ModelAttribute("singer") Singer singer) {

        singerService.save(singer);
        ModelAndView modelAndView = new ModelAndView("/singer/create");
        modelAndView.addObject("singer", new Singer());
        modelAndView.addObject("message", "New Singer Created Successfully");
        return modelAndView;
    }

    @GetMapping
    public ModelAndView listSingers(@RequestParam("searchName") Optional<String> name, @PageableDefault(value =3) Pageable pageable){
        String searchName = name.orElse("");
        Page<Singer> singers = singerService.findAllByNameContaining(searchName,pageable);
        ModelAndView modelAndView = new ModelAndView("singer/list");
        modelAndView.addObject("singers", singers);
        return modelAndView;
    }
    @GetMapping("/{id}")
    public String viewSinger(@PathVariable Long id, Model model){
        Singer singers = singerService.findById(id).get();
        model.addAttribute("singer",singers);
        return "singer/view";
    }

    @GetMapping("/edit-singer/{id}")
    public ModelAndView showEditForm(@PathVariable Long id){
        Optional<Singer> singer = singerService.findById(id);
        if(singer.isPresent() ) {
            ModelAndView modelAndView = new ModelAndView("singer/edit");
            modelAndView.addObject("singer", singer);
            return modelAndView;

        }
        else {
//            ModelAndView modelAndView = new ModelAndView("/error.404");
            return null;
        }
    }

    @PostMapping("/edit-singer")
    public ModelAndView updateBlog(@ModelAttribute("singer") Singer singer){
        singerService.save(singer);
        System.out.println(singer.toString());
        ModelAndView modelAndView = new ModelAndView("singer/edit");
        modelAndView.addObject("singer",singer);
        modelAndView.addObject("message","Singer updated sucessfully");
        return modelAndView;
    }

    @GetMapping("/delete-singer/{id}")
    public ModelAndView showdeleteForm(@PathVariable Long id){
        Singer singer = singerService.findById(id).get();
        if(singer!=null){
            ModelAndView modelAndView = new ModelAndView("singer/delete");
            modelAndView.addObject("singer",singer);
            return modelAndView;
        }
        else {
//            ModelAndView modelAndView = new ModelAndView("/error.404");
            return null;
        }
    }

    @PostMapping("delete-singer")
    public String deleteBlog(@ModelAttribute("singer") Singer singer){
        singerService.remove(singer.getId());
        return "redirect:singers";
    }
}
