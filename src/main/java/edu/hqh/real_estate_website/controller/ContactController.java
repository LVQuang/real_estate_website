package edu.hqh.real_estate_website.controller;

import edu.hqh.real_estate_website.service.ContactService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/contact")
@Controller
public class ContactController {
    ContactService contactService;

    @GetMapping("/{pageNumber}")
    String getContact(Model model,
                   @RequestParam(name = "page",
                           required = false, defaultValue = "1") Integer pageNumber
    )
    {
        if (pageNumber == null)
            pageNumber = 1;
        var result = contactService.getAllContactsPage(pageNumber);
        var contacts = result.getContent();
        model.addAttribute("contacts", contacts);
        model.addAttribute("totalPages", result.getTotalPages() - 1);
        if(result.getTotalPages() == 0) {
            return "/layout/contacts";
        }
        if(result.getTotalPages() <= pageNumber)
            return "redirect:/contact/1?outPage";
        return "/layout/contacts";
    }

    @GetMapping("/user/{pageNumber}")
    String getMyContact(Model model,
                      @RequestParam(name = "page",
                              required = false, defaultValue = "1") Integer pageNumber
    )
    {
        if (pageNumber == null)
            pageNumber = 1;
        var result = contactService.getAllContactsUserPage(pageNumber);
        var contacts = result.getContent();
        model.addAttribute("contacts", contacts);
        model.addAttribute("totalPages", result.getTotalPages() - 1);
        if(result.getTotalPages() == 0) {
            return "/layout/userContacts";
        }
        if(result.getTotalPages() <= pageNumber)
            return "redirect:/contact/user/1?outPage";
        return "layout/userContacts";
    }


}
