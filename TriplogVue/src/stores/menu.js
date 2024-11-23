import { ref } from "vue";
import { defineStore } from "pinia";

export const useMenuStore = defineStore("menuStore", () => {
  const menuList = ref([
    { name: "회원가입", show: true, routeName: "user-join" },
    { name: "로그인", show: true, routeName: "user-login" },
    { name: "todo", show: false, routeName: "todos" },
    { name: "내정보", show: false, routeName: "user-mypage" },
    { name: "로그아웃", show: false, routeName: "user-logout" }
  ]);


  const changeMenuState = (value) => {
    if(value){
      menuList.value[0].show = false;
      menuList.value[1].show = false;
      menuList.value[2].show = true;
      menuList.value[3].show = true;
      menuList.value[4].show = true;
    }
    else{
      menuList.value[0].show = true;
      menuList.value[1].show = true;
      menuList.value[2].show = false;
      menuList.value[3].show = false;
      menuList.value[4].show = false;
    }
    //menuList.value = menuList.value.map((item) => ({ ...item, show: !item.show }));
  };
  return {
    menuList,
    changeMenuState,
  };
},
{persist: { storage: localStorage }});

