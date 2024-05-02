package edu.hqh.real_estate_website.controller;

import edu.hqh.real_estate_website.dto.request.UserRequest;
import edu.hqh.real_estate_website.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/user")
@Controller
public class UserController {
    UserService userService;

    @GetMapping("/myInfo")
    String getMyInfo(Model model) {
        var id = userService.getCurrentUser().getId();
        var request = UserRequest.builder()
                .id(id)
                .build();
        log.info(request.getId());
        var response = userService.getById(request.getId());
        model.addAttribute("response",response);
        return "/user/updateMyInfo";
    }

    @GetMapping("/userDetail/{userId}")
    String getUserDetail(Model model,@PathVariable String userId){
        var user = userService.getById(userId);
        model.addAttribute("user", user);
        return "online_template/userDetail";
    }
    @GetMapping("/{pageNumber}")
    String getUsers(Model model,
                   @RequestParam(name = "page",
                           required = false, defaultValue = "0") Integer pageNumber
    )
    {
        if (pageNumber != null && pageNumber > 0)
            pageNumber-=1;
        if(pageNumber == null)
            pageNumber =0;

        var result = userService.getAllUsersPage(pageNumber);
        var users = result.getContent();

        log.info(String.valueOf(users.isEmpty()));

        model.addAttribute("users", users);
        model.addAttribute("totalPages", result.getTotalPages());
        if(result.getTotalPages() == 0) {
            return "/layout/users";
        }
        if(result.getTotalPages() <= pageNumber)
            return "redirect:/user/null?page=0&outPage=true";
        return "/layout/users";
    }

}
