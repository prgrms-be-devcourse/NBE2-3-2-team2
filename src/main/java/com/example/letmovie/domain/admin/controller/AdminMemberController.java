package com.example.letmovie.domain.admin.controller;

import com.example.letmovie.domain.admin.service.AdminMemberServiceImpl;
import com.example.letmovie.domain.member.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminMemberController {
    @Autowired
    private AdminMemberServiceImpl adminService;

    // /admin/member : 회원정보
    @GetMapping("/member")
    public String member() {
        return "admin/member/admin_member";
    }

    // /admin/member/search : 회원정보검색후
    @GetMapping("/member/search")
    public String memberSearch(@RequestParam("inputnickname") String nickname, Model model) {
        try {
            List<Member> members = adminService.findMemberByName(nickname);
            if(members != null) {
                model.addAttribute("members", members);
            } else {
                model.addAttribute("error", "검색 결과가 없습니다. 검색어를 확인해주세요.");
            }
        } catch (Exception e) {
            model.addAttribute("error", "오류가 발생했습니다. 다시 시도해주세요.");
        }
        return "admin/member/admin_member_search";
    }

    // /admin/member/modify : ID로 특정 회원 조회
    @GetMapping("/member/modify")
    public String modifyMemberById(@RequestParam("id") Long memberId, Model model) {
        try {
            Member member = adminService.findMemberById(memberId);
            if (member != null) {
                model.addAttribute("member", member);
            } else {
                model.addAttribute("error", "해당 ID에 해당하는 회원이 없습니다.");
            }
        } catch (Exception e) {
            model.addAttribute("error", "오류가 발생했습니다. 다시 시도해주세요.");
        }
        return "admin/member/admin_member_modify";
    }

    // /admin/member/modify/ok : 회원 수정 처리
    @PostMapping("/member/modify/ok")
    public String modifyMemberOk(@ModelAttribute Member member, RedirectAttributes redirectAttributes) {
        try {
            adminService.updateMember(member);
            redirectAttributes.addFlashAttribute("success", "회원 정보가 성공적으로 수정되었습니다.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "오류가 발생했습니다. 수정에 실패했습니다.");
        }
        return "redirect:/admin/member";
    }

}
