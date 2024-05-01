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

    @GetMapping("/addContact")
    String getAddContact()
    {
        return "/addContact";
    }

    @GetMapping("/{pageNumber}")
    String getContact(Model model,
                   @RequestParam(name = "page",
                           required = false, defaultValue = "0") Integer pageNumber
    )
    {

        if (pageNumber != null && pageNumber > 0)
            pageNumber-=1;
        if(pageNumber == null)
            pageNumber =0;

        var result = contactService.getAllContactsPage(pageNumber);
        var contacts = result.getContent();
        model.addAttribute("contacts", contacts);
        model.addAttribute("totalPages", result.getTotalPages());
        if(result.getTotalPages() == 0) {
            return "/layout/contacts";
        }
        if(result.getTotalPages() <= pageNumber)
            return "redirect:/contact/null?page=0&outPage=true";
        return "/layout/contacts";
    }

    @GetMapping("/user/{pageNumber}")
    String getMyContact(Model model,
                      @RequestParam(name = "page",
                              required = false, defaultValue = "0") Integer pageNumber
    )
    {

        if (pageNumber != null && pageNumber > 0)
            pageNumber-=1;
        if(pageNumber == null)
            pageNumber =0;

        var result = contactService.getAllContactsUserPage(pageNumber);
        var contacts = result.getContent();
        model.addAttribute("contacts", contacts);
        model.addAttribute("totalPages", result.getTotalPages());
        if(result.getTotalPages() == 0) {
            return "/layout/userContacts";
        }
        if(result.getTotalPages() <= pageNumber)
            return "redirect:/contact/user/null?page=0&outPage=true";
        return "layout/userContacts";
    }

}
