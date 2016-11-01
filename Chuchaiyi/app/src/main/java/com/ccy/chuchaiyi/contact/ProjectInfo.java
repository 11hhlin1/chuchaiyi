package com.ccy.chuchaiyi.contact;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Chuck on 2016/9/9.
 */
public class ProjectInfo implements Serializable{
    private static final long serialVersionUID = -3128266797277027893L;

    /**
     * Code : 0
     * Message : string
     * TotalCount : 0
     * Projects : [{"ProjectId":0,"ProjectCode":"string","ProjectName":"string","StartDate":"string","ExpiredDate":"string","TotalAmount":0}]
     */

    private int Code;
    private String Message;
    private int TotalCount;
    /**
     * ProjectId : 0
     * ProjectCode : string
     * ProjectName : string
     * StartDate : string
     * ExpiredDate : string
     * TotalAmount : 0
     */

    private List<ProjectsBean> Projects;

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public int getTotalCount() {
        return TotalCount;
    }

    public void setTotalCount(int TotalCount) {
        this.TotalCount = TotalCount;
    }

    public List<ProjectsBean> getProjects() {
        return Projects;
    }

    public void setProjects(List<ProjectsBean> Projects) {
        this.Projects = Projects;
    }

    public static class ProjectsBean implements Serializable{
        private static final long serialVersionUID = 1033498790719042475L;
        private int ProjectId;
        private String ProjectCode;
        private String ProjectName;
        private String StartDate;
        private String ExpiredDate;
        private int TotalAmount;

        public int getProjectId() {
            return ProjectId;
        }

        public void setProjectId(int ProjectId) {
            this.ProjectId = ProjectId;
        }

        public String getProjectCode() {
            return ProjectCode;
        }

        public void setProjectCode(String ProjectCode) {
            this.ProjectCode = ProjectCode;
        }

        public String getProjectName() {
            return ProjectName;
        }

        public void setProjectName(String ProjectName) {
            this.ProjectName = ProjectName;
        }

        public String getStartDate() {
            return StartDate;
        }

        public void setStartDate(String StartDate) {
            this.StartDate = StartDate;
        }

        public String getExpiredDate() {
            return ExpiredDate;
        }

        public void setExpiredDate(String ExpiredDate) {
            this.ExpiredDate = ExpiredDate;
        }

        public int getTotalAmount() {
            return TotalAmount;
        }

        public void setTotalAmount(int TotalAmount) {
            this.TotalAmount = TotalAmount;
        }
    }
}
