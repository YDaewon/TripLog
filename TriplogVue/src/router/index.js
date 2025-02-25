import { createRouter, createWebHistory } from "vue-router";
import TheMainView from "../views/TheMainView.vue";
import TheAttractionView from "@/views/TheAttractionView.vue";
// import TheBoardView from "../views/TheBoardView.vue";

import { storeToRefs } from "pinia";

import { useMemberStore } from "@/stores/member";

const onlyAuthUser = (to, from, next) => {
  const memberStore = useMemberStore();
  const { userInfo, isValidToken } = storeToRefs(memberStore);
  const { getUserInfo } = memberStore;

  let token = sessionStorage.getItem("accessToken");
  if (token && userInfo.value != null) {
    getUserInfo(token);
  }
  //console.log("isValidToken: " + isValidToken.value)
  //console.log("token: " + token)
  if (!isValidToken.value || userInfo.value === null || token === null) {
    next({ name: "user-login" });
  } else {
    next();
  }
};

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "main",
      component: TheMainView,
    },
    {
      path: "/user",
      name: "user",
      component: () => import("@/views/TheUserView.vue"),
      children: [
        {
          path: "login",
          name: "user-login",
          component: () => import("@/components/users/UserLogin.vue"),
        },
        {
          path: "join",
          name: "user-join",
          component: () => import("@/components/users/UserRegister.vue"),
        },
        {
          path: "mypage",
          name: "user-mypage",
          beforeEnter: onlyAuthUser,
          component: () => import("@/components/users/UserMyPage.vue"),
        },
      ],
    },
    {
      path: "/board",
      name: "board",
      // component: TheBoardView,
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import("../views/TheBoardView.vue"),
      redirect: { name: "article-list" },
      children: [
        {
          path: "list",
          name: "article-list",
          beforeEnter: onlyAuthUser,
          component: () => import("@/components/boards/AllBoard.vue"),
        },
        {
          path: "my",
          name: "my-article-list",
          beforeEnter: onlyAuthUser,
          component: () => import("@/components/boards/MyBoard.vue"),
        },
        {
          path: "star",
          name: "star-article-list",
          beforeEnter: onlyAuthUser,
          component: () => import("@/components/boards/StarBoard.vue"),
        },
        {
          path: ":articleno",
          name: "article-view",
          beforeEnter: onlyAuthUser,
          component: () => import("@/components/boards/BoardDetail.vue"),
        },
        {
          path: "write",
          name: "article-write",
          beforeEnter: onlyAuthUser,
          component: () => import("@/components/boards/BoardWrite.vue"),
        },
        {
          path: "modify/:articleno",
          name: "article-modify",
          beforeEnter: onlyAuthUser,
          component: () => import("@/components/boards/BoardModify.vue"),
        },
      ],
    },
    {
      path: "/attraction",
      name: "attractions",
      component: TheAttractionView,
    },
    {
      path: "/todos",
      name: "todos",
      beforeEnter: onlyAuthUser,
      component: () => import("@/views/TheTodoView.vue"),
    },
    {
      path: "/plan",
      name: "plan",
      beforeEnter: onlyAuthUser,
      component: () => import("@/views/ThePlanView.vue"),
    },
    {
      path: "/planDetail/:planNo/:isArticle",
      name: "planDetail",
      beforeEnter: onlyAuthUser,
      component: () => import("@/views/ThePlanDetailView.vue"),
    },
  ],
});

export default router;
