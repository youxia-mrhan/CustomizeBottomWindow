package com.example.myapplication.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SortWindowBean {

    @SerializedName("all_store_list")
    private List<AllStoreListDTO> allStoreList;
    @SerializedName("all_sort_list")
    private List<AllSortListDTO> allSortList;
    @SerializedName("recommend_sort_list")
    private List<RecommendSortListDTO> recommendSortList;

    public List<AllStoreListDTO> getAllStoreList() {
        return allStoreList;
    }

    public void setAllStoreList(List<AllStoreListDTO> allStoreList) {
        this.allStoreList = allStoreList;
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

    public static class AllStoreListDTO {
        @SerializedName("all_store_id")
        private Integer allStoreId;
        @SerializedName("all_store_title")
        private String allStoreTitle;
        @SerializedName("all_store_child")
        private List<AllStoreChildDTO> allStoreChild;

        public Integer getAllStoreId() {
            return allStoreId;
        }

        public void setAllStoreId(Integer allStoreId) {
            this.allStoreId = allStoreId;
        }

        public String getAllStoreTitle() {
            return allStoreTitle;
        }

        public void setAllStoreTitle(String allStoreTitle) {
            this.allStoreTitle = allStoreTitle;
        }

        public List<AllStoreChildDTO> getAllStoreChild() {
            return allStoreChild;
        }

        public void setAllStoreChild(List<AllStoreChildDTO> allStoreChild) {
            this.allStoreChild = allStoreChild;
        }

        public static class AllStoreChildDTO {
            @SerializedName("all_store_child_id")
            private Integer allStoreChildId;
            @SerializedName("all_store_child_title")
            private String allStoreChildTitle;
            @SerializedName("all_store_child_detail")
            private List<AllStoreChildDetailDTO> allStoreChildDetail;

            public Integer getAllStoreChildId() {
                return allStoreChildId;
            }

            public void setAllStoreChildId(Integer allStoreChildId) {
                this.allStoreChildId = allStoreChildId;
            }

            public String getAllStoreChildTitle() {
                return allStoreChildTitle;
            }

            public void setAllStoreChildTitle(String allStoreChildTitle) {
                this.allStoreChildTitle = allStoreChildTitle;
            }

            public List<AllStoreChildDetailDTO> getAllStoreChildDetail() {
                return allStoreChildDetail;
            }

            public void setAllStoreChildDetail(List<AllStoreChildDetailDTO> allStoreChildDetail) {
                this.allStoreChildDetail = allStoreChildDetail;
            }

            public static class AllStoreChildDetailDTO {
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
