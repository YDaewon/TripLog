import { ref } from "vue";
import { defineStore } from "pinia";
import { listAttractions } from "@/api/attraction";
import { listContentType } from "@/api/map";

export const useAttractionStore = defineStore(
  "attractionStore",
  () => {
    const attractions = ref([]);
    const selectedAttraction = ref({});
    const getAttractions = (searchParam) => {
      listContentType(
        (response) => {
          const contentTypes = response.data;
          console.log("Extracted content types:", contentTypes);

          listAttractions(
            searchParam,
            ({ data }) => {
              console.log("Raw attractions data:", data);

              // contentTypeName을 추가하여 새로운 배열 생성
              const updatedAttractions = data.map((attraction) => {
                const match = contentTypes.find(
                  (type) => type.content_type_id === attraction.contentTypeId
                );
                return {
                  ...attraction, // 기존 속성 복사
                  contentTypeName: match ? match.content_type_name : null, // contentTypeName 추가
                };
              });
              attractions.value = updatedAttractions;
            },
            (err) => {
              console.log("Error fetching attractions:", err);
            }
          );
        },
        (err) => {
          console.log("Error fetching content types:", err);
        }
      );
    };
    return { attractions, selectedAttraction, getAttractions };
  },
  { persist: { storage: localStorage } }
);
