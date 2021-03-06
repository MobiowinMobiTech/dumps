package com.mobiowin.paalansocial.payload.request;

import com.mobiowin.paalansocial.helper.Social;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created on 6/1/17.
 * Author Dharmendra
 * Company CmssPhyder
 */

public class OrganizationReqCreateAchievments {

    private String entity;

    private Data data;

    private String action;

    private String type;

    public static OrganizationReqCreateAchievments get(String orgs, String achiId, ArrayList<String> imgs, String description, String others,
                                                       String subTitle, String title, String name, String action, String notifyId) {

        Achievementimg[] achievements = new Achievementimg[imgs.size()];
        for (int i = 0; i < achievements.length; i++) {
            Achievementimg achivementimg = new Achievementimg();
            achivementimg.setImg(imgs.get(i));
            achievements[i] = achivementimg;
        }

        Data data = new Data();
        data.setNotificationId(notifyId);
        data.setOrgid(orgs);
        data.setAchiId(achiId);
        data.setName(name);
        data.setDiscription(description);
        data.setOthers(others);
        data.setSubtitle(subTitle);
        data.setTitle(title);
        data.setAchievementimg(achievements);

        OrganizationReqCreateAchievments createAchievments = new OrganizationReqCreateAchievments();
        createAchievments.setData(data);
        createAchievments.setAction(action);
        createAchievments.setEntity(Social.ORG_ENTITY);
        createAchievments.setType(Social.ACHIEVEMENT_TYPE);

        return createAchievments;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "OrganizationReqCreateAchievments{" +
                "entity='" + entity + '\'' +
                ", data=" + data +
                ", action='" + action + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public static class Data {

        private String orgName;

        private String title;

        private String others;

        private Achievementimg[] achievementimg;

        private String orgid;

        private String achievementid;

        private String subtitle;

        private String discription;

        private String notificationid;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getName() {
            return orgName;
        }

        public void setName(String orgName) {
            this.orgName = orgName;
        }


        public String getOthers() {
            return others;
        }

        public void setOthers(String others) {
            this.others = others;
        }

        public Achievementimg[] getAchievementimg() {
            return achievementimg;
        }

        public void setAchievementimg(Achievementimg[] achievementimg) {
            this.achievementimg = achievementimg;
        }

        public String getOrgid() {
            return orgid;
        }

        public void setOrgid(String orgid) {
            this.orgid = orgid;
        }

        public String getAchiId() {
            return achievementid;
        }

        public void setAchiId(String achievementid) {
            this.achievementid = achievementid;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public String getDiscription() {
            return discription;
        }

        public void setDiscription(String discription) {
            this.discription = discription;
        }

        public String getNotificationId() {
            return notificationid;
        }

        public void setNotificationId(String notificationid) {
            this.notificationid = notificationid;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "orgName='" + orgName + '\'' +
                    "title='" + title + '\'' +
                    ", others='" + others + '\'' +
                    ", achievementimg=" + Arrays.toString(achievementimg) +
                    ", orgid='" + orgid + '\'' +
                    ", achievementid='" + achievementid + '\'' +
                    ", subtitle='" + subtitle + '\'' +
                    ", discription='" + discription + '\'' +
                    ", notificationid='" + notificationid + '\'' +
                    '}';
        }
    }

    public static class Achievementimg {
        private String img;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        @Override
        public String toString() {
            return "Achievementimg{" +
                    "img='" + img + '\'' +
                    '}';
        }
    }

}
