package com.haezuo.newmit.mypage.Controller;

import com.haezuo.newmit.common.CommonService.BaseService;
import com.haezuo.newmit.common.Util.CommonUtil;
import com.haezuo.newmit.login.service.LoginService;
import com.haezuo.newmit.login.util.TokenUtil;
import com.haezuo.newmit.mypage.Service.MyPageService;
import com.haezuo.newmit.recipe.service.RecipeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.haezuo.newmit.common.constants.*;

@Controller
@RequiredArgsConstructor
public class MyPageController extends BaseService {

    private final RecipeService recipeService;

    private final MyPageService myPageService;

    private final LoginService loginService;

    @RequestMapping(value = "/myPage/viewMyPage", method = RequestMethod.GET)
    public ModelAndView viewMyPage(HttpServletRequest request) throws IOException {
        ModelAndView mav = new ModelAndView();

        // HTTP 요청 헤더에 존재하는 쿠키에서 인증 토큰을 추출함
        String token = new TokenUtil().getTokenByCookies(request);

        if(token != null && !"".equals(token)) {
            Map<String, Object> condition = new HashMap<>();
            String currentUserId = loginService.getCurrentUserId(request);
            condition.put("userId", currentUserId);

            mav.addObject("writtenRecipeCount", recipeService.getWrittenRecipeCount(condition));

            mav.addObject("bookmarkCount", recipeService.getBookmarkCountByMbNo(currentUserId));

            mav.addObject("userRole", loginService.ConnectUserInfo(request, userInfo.KEY_USER_ROLE));

            String profileImageId = myPageService.getUserProfileImageIdByMbNo(currentUserId);

            if(profileImageId != null)
                mav.addObject("userProfileImage", CommonUtil.convertFileToBase64(getFileByFileId(myPageService.getUserProfileImageIdByMbNo(currentUserId))));

            mav.setViewName("/mypage/myPage");
        } else {
            mav.setViewName("redirect:/login");
        }

        return mav;
    }

    @RequestMapping(value = "/myPage/viewNotice", method = RequestMethod.GET)
    public ModelAndView viewNotice(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();

        // HTTP 요청 헤더에 존재하는 쿠키에서 인증 토큰을 추출함
        String token = new TokenUtil().getTokenByCookies(request);

        if(token != null && !"".equals(token)) {
            mav.setViewName("/mypage/notice");
            mav.addObject("userNoticeInfoList", myPageService.getUserNoticeInfoListByMbNo(loginService.ConnectUserInfo(request, userInfo.KEY_USER_ID)));
        } else {
            mav.setViewName("redirect:/login");
        }


        return mav;
    }

    @RequestMapping(value = "/myPage/saveProfileImage", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveProfileImage(HttpServletRequest request, @RequestBody Map<String, Object> requestBody) {
        Map<String, Object> result = new HashMap<>();

        Map<String, Object> imageInfo = new HashMap<>();
        imageInfo.put("profileImage", (String) requestBody.get("profileImage"));
        imageInfo.put("profileImageNm", (String) requestBody.get("profileImageNm"));

        myPageService.saveProfileImage(imageInfo, loginService.getCurrentUserId(request));

        return result;
    }

    @RequestMapping(value="/myPage/{type}Bookmark", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addBookmark(HttpServletRequest request, @RequestBody Map<String, Object> requestBody, @PathVariable("type") String reqType) {
        Map<String, Object> result = new HashMap<>();

        String recipeNo = (String) requestBody.get("recipeNo");
        String mbNo = loginService.getCurrentUserId(request);

        if("add".equals(reqType)) {
            myPageService.insertMemberBookmark(recipeNo, mbNo);
        } else if("remove".equals(reqType)) {
            myPageService.deleteMemberBookmark(recipeNo, mbNo);
        }

        return result;
    }

    @GetMapping(value = "/myPage/viewBookmarkRecipeList")
    public ModelAndView viewBookmarkRecipeList(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("/recipe/bookmarkRecipeList");

        return mav;
    }

    @PostMapping(value = "/mypage/selectBookmarkRecipeList")
    @ResponseBody
    public Map<String, Object> selectBookmarkRecipeList(HttpServletRequest request) throws IOException {
        Map<String, Object> result = new HashMap<>();

        result.put("bookmarkRecipeList", recipeService.getBookmarkRecipeListByMbNo(loginService.getCurrentUserId(request)));

        return result;
    }

}
