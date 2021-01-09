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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/singers")
public class SingerController {

    @Autowired
    SingerService singerService;

    @Autowired
    Environment env;

    @GetMapping("/create")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("/singer/create");
        modelAndView.addObject("singer", new Singer());
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView saveSinger(@ModelAttribute("singer") Singer singer) {
        singerService.save(singer);
        ModelAndView modelAndView = new ModelAndView("/singer/create");
        modelAndView.addObject("singer", new Singer());
        modelAndView.addObject("message", "New Singer Created Successfully");
        return modelAndView;
    }

    @GetMapping
    public ModelAndView listSingers(@RequestParam("searchName") Optional<String> name, @PageableDefault(value = 3) Pageable pageable) {
        Page<Singer> singers; // Tạo đối tượng lưu Page singers;
        if (name.isPresent()) {
            // Kiểm tra xem nếu Parameter search được truyền vào thì gọi service có 2 tham số
            singers = singerService.findAllByNameContains(name.get(), pageable);
        } else {
            // Nếu không có search thì gọi service có 1 tham số
            singers = singerService.findAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("singer/list");
        modelAndView.addObject("singers", singers);
        return modelAndView;
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        Singer singers = singerService.findById(id).get();
        model.addAttribute("singer", singers);
        return "singer/view";
    }

    @GetMapping("/edit-singer/{id}")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirect) {
        Optional<Singer> singer = singerService.findById(id);
        if (singer.isPresent()) {
            model.addAttribute("singer", singer.get());
            return "singer/edit";
        } else {
            redirect.addFlashAttribute("message", "Singer not found!");
            return "redirect:/singers";
        }
    }

    @PostMapping("/edit-singer")
    public ModelAndView updateBlog(@ModelAttribute("singer") Singer singer) {
        singerService.save(singer);
        System.out.println(singer.toString());
        ModelAndView modelAndView = new ModelAndView("singer/edit");
        modelAndView.addObject("singer", singer);
        modelAndView.addObject("message", "Singer updated sucessfully");
        return modelAndView;
    }

    @GetMapping("/delete-singer/{id}")
    public String showDeleteForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirect) {
        Optional<Singer> singer = singerService.findById(id);
        if (singer.isPresent()) {
            model.addAttribute("singer", singer.get());
            return "singer/delete";
        } else {
            redirect.addFlashAttribute("message", "Singer not found");
            return "redirect:/singers";
        }
    }

    @PostMapping("delete-singer")
    public String deleteBlog(@ModelAttribute("singer") Singer singer, RedirectAttributes redirect) {
        singerService.remove(singer.getId());
        redirect.addFlashAttribute("message", "Deleted successfully.");
        return "redirect:/singers";
    }
}
