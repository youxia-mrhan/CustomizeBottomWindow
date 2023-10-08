package com.example.myapplication.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SortDownBean {

    @SerializedName("all_shopping_list")
    private List<AllShoppingListDTO> allShoppingList;
    @SerializedName("all_sort_list")
    private List<AllSortListDTO> allSortList;
    @SerializedName("recommend_sort_list")
    private List<RecommendSortListDTO> recommendSortList;

    public List<AllShoppingListDTO> getAllShoppingList() {
        return allShoppingList;
    }

    public void setAllShoppingList(List<AllShoppingListDTO> allShoppingList) {
        this.allShoppingList = allShoppingList;
    }

    public List<AllSortListDTO> getAllSortList() {
        return allSortList;
    }

    public void setAllSortList(List<AllSortListDTO> allSortList) {
        this.allSortList = allSortList;
    }

    public List<RecommendSortListDTO> getRecommendSortList() {
        return recommendSortList;
    }

    public void setRecommendSortList(List<RecommendSortListDTO> recommendSortList) {
        this.recommendSortList = recommendSortList;
    }

    public static class AllShoppingListDTO {
        @SerializedName("all_shopping_id")
        private Integer allShoppingId;
        @SerializedName("all_shopping_title")
        private String allShoppingTitle;
        @SerializedName("all_shopping_child")
        private List<AllShoppingChildDTO> allShoppingChild;

        public Integer getAllShoppingId() {
            return allShoppingId;
        }

        public void setAllShoppingId(Integer allShoppingId) {
            this.allShoppingId = allShoppingId;
        }

        public String getAllShoppingTitle() {
            return allShoppingTitle;
        }

        public void setAllShoppingTitle(String allShoppingTitle) {
            this.allShoppingTitle = allShoppingTitle;
        }

        public List<AllShoppingChildDTO> getAllShoppingChild() {
            return allShoppingChild;
        }

        public void setAllShoppingChild(List<AllShoppingChildDTO> allShoppingChild) {
            this.allShoppingChild = allShoppingChild;
        }

        public static class AllShoppingChildDTO {
            @SerializedName("all_shopping_child_id")
            private Integer allShoppingChildId;
            @SerializedName("all_shopping_child_title")
            private String allShoppingChildTitle;
            @SerializedName("all_shopping_child_detail")
            private List<AllShoppingChildDetailDTO> allShoppingChildDetail;

            public Integer getAllShoppingChildId() {
                return allShoppingChildId;
            }

            public void setAllShoppingChildId(Integer allShoppingChildId) {
                this.allShoppingChildId = allShoppingChildId;
            }

            public String getAllShoppingChildTitle() {
                return allShoppingChildTitle;
            }

            public void setAllShoppingChildTitle(String allShoppingChildTitle) {
                this.allShoppingChildTitle = allShoppingChildTitle;
            }

            public List<AllShoppingChildDetailDTO> getAllShoppingChildDetail() {
                return allShoppingChildDetail;
            }

            public void setAllShoppingChildDetail(List<AllShoppingChildDetailDTO> allShoppingChildDetail) {
                this.allShoppingChildDetail = allShoppingChildDetail;
            }

            public static class AllShoppingChildDetailDTO {
                @SerializedName("location_id")
                private Integer locationId;
                @SerializedName("location_distance")
                private String locationDistance;

                public Integer getLocationId() {
                    return locationId;
                }

                public void setLocationId(Integer locationId) {
                    this.locationId = locationId;
                }

                public String getLocationDistance() {
                    return locationDistance;
                }

                public void setLocationDistance(String locationDistance) {
                    this.locationDistance = locationDistance;
                }
            }
        }
    }

    public static class AllSortListDTO {
        @SerializedName("all_sort_id")
        private Integer allSortId;
        @SerializedName("all_sort_title")
        private String allSortTitle;
        @SerializedName("all_sort_child")
        private List<AllSortChildDTO> allSortChild;

        public Integer getAllSortId() {
            return allSortId;
        }

        public void setAllSortId(Integer allSortId) {
            this.allSortId = allSortId;
        }

        public String getAllSortTitle() {
            return allSortTitle;
        }

        public void setAllSortTitle(String allSortTitle) {
            this.allSortTitle = allSortTitle;
        }

        public List<AllSortChildDTO> getAllSortChild() {
            return allSortChild;
        }

        public void setAllSortChild(List<AllSortChildDTO> allSortChild) {
            this.allSortChild = allSortChild;
        }

        public static class AllSortChildDTO {
            @SerializedName("all_sort_child_id")
            private Integer allSortChildId;
            @SerializedName("all_sort_child_title")
            private String allSortChildTitle;

            public Integer getAllSortChildId() {
                return allSortChildId;
            }

            public void setAllSortChildId(Integer allSortChildId) {
                this.allSortChildId = allSortChildId;
            }

            public String getAllSortChildTitle() {
                return allSortChildTitle;
            }

            public void setAllSortChildTitle(String allSortChildTitle) {
                this.allSortChildTitle = allSortChildTitle;
            }
        }
    }

    public static class RecommendSortListDTO {
        @SerializedName("recommend_sort_id")
        private Integer recommendSortId;
        @SerializedName("recommend_sort_title")
        private String recommendSortTitle;

        public Integer getRecommendSortId() {
            return recommendSortId;
        }

        public void setRecommendSortId(Integer recommendSortId) {
            this.recommendSortId = recommendSortId;
        }

        public String getRecommendSortTitle() {
            return recommendSortTitle;
        }

        public void setRecommendSortTitle(String recommendSortTitle) {
            this.recommendSortTitle = recommendSortTitle;
        }
    }
}
