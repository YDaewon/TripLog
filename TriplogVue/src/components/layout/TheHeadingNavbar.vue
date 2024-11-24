<script setup>
import { onMounted } from "vue";
import { useMenuStore } from "@/stores/menu";
import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";

const menuStore = useMenuStore();
const memberStore = useMemberStore();



// 반응형을 유지하면서 스토어에서 속성을 추출하려면, storeToRefs()를 사용
// https://pinia.vuejs.kr/core-concepts/
const { menuList } = storeToRefs(menuStore);
const { changeMenuState } = menuStore;

const { isValidToken, userInfo } = storeToRefs(memberStore);
const { userLogout } = memberStore;

onMounted(() => {
  let token = sessionStorage.getItem("accessToken");
  if (token == null) {
    isValidToken.value = false;
    userInfo.value = null;
    changeMenuState(false);
  }

})

const logout = () => {
  userLogout();
  changeMenuState(false);
};
</script>

<template>
  <nav class="navbar navbar-expand-lg bg-body-tertiary sticky-top">
    <div class="container-fluid">
      <router-link :to="{ name: 'main' }" class="navbar-brand">
        <img src="@/assets/ssafy_logo.png" class="rounded mx-auto d-block" alt="..." style="height: 100px"/>
      </router-link>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarScroll"
        aria-controls="navbarScroll" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarScroll">
        <ul class="navbar-nav me-auto my-2 my-lg-0 navbar-nav-scroll" style="--bs-scroll-height: 100px">
          <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
              여행계획
            </a>
            <ul class="dropdown-menu">
              <li>
                <a class="dropdown-item">
                  <router-link :to="{ name: 'plan' }" class="nav-link">
                    내 여행계획
                  </router-link>
                </a>
              </li>
              <a class="dropdown-item">
                <router-link :to="{ name: 'plan' }" class="nav-link">
                  여행계획 목록
                </router-link>
              </a>
              <a class="dropdown-item">
                <router-link :to="{ name: 'board' }" class="nav-link">
                  여행계획 즐겨찾기
                </router-link>
              </a>
            </ul>
          </li>
          <li><router-link :to="{ name: 'board' }" class="nav-link">전체 게시판</router-link></li>
          <li class="nav-item">
            <router-link :to="{ name: 'attractions' }" class="nav-link">
              여행지 검색
            </router-link>
          </li>
        </ul>
        <!-- <form class="d-flex" role="search">
          <input
            class="form-control me-2"
            type="search"
            placeholder="검색..."
            aria-label="Search"
          />
          <button class="btn btn-outline-success" type="button">search</button>
        </form> -->
        <ul class="navbar-nav ms-auto my-2 my-lg-0 navbar-nav-scroll" style="--bs-scroll-height: 100px">
          <template v-for="menu in menuList" :key="menu.routeName">
            <template v-if="menu.show">
              <template v-if="menu.routeName === 'user-logout'">
                <li class="nav-item">
                  <router-link
                    to="/"
                    @click.prevent="logout"
                    class="nav-link"
                    >{{ menu.name }}</router-link
                  >
                </li>
              </template>
              <template v-else>
                <li class="nav-item">
                  <router-link
                    :to="{ name: menu.routeName }"
                    class="nav-link"
                    >{{ menu.name }}</router-link
                  >
                </li>
              </template>
            </template>
          </template>
        </ul>
      </div>
    </div>
  </nav>
</template>

<style scoped></style>
