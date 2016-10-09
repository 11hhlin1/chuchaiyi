package com.ccy.chuchaiyi.order;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Chuck on 2016/10/9.
 */
public class DepartmentRsp implements Serializable{
    private static final long serialVersionUID = -8187763865947189584L;

    /**
     * Id : 1
     * Name : 出差易
     * ParentId : null
     * SortNo : 1
     * ChildDepartments : [{"Id":2,"Name":"总经办","ParentId":1,"SortNo":1,"ChildDepartments":[]},{"Id":4,"Name":"综合部","ParentId":1,"SortNo":1,"ChildDepartments":[{"Id":3,"Name":"人力资源部","ParentId":4,"SortNo":2,"ChildDepartments":[]},{"Id":13,"Name":"行政部","ParentId":4,"SortNo":0,"ChildDepartments":[]}]},{"Id":14,"Name":"财务部","ParentId":1,"SortNo":0,"ChildDepartments":[]},{"Id":15,"Name":"研发部","ParentId":1,"SortNo":0,"ChildDepartments":[{"Id":16,"Name":"技术部","ParentId":15,"SortNo":0,"ChildDepartments":[]},{"Id":17,"Name":"网络部","ParentId":15,"SortNo":0,"ChildDepartments":[]},{"Id":18,"Name":"云计算数据中心","ParentId":15,"SortNo":0,"ChildDepartments":[]}]},{"Id":19,"Name":"客户服务部","ParentId":1,"SortNo":0,"ChildDepartments":[{"Id":20,"Name":"售前服务部","ParentId":19,"SortNo":0,"ChildDepartments":[{"Id":21,"Name":"售前一部","ParentId":20,"SortNo":0,"ChildDepartments":[]}]},{"Id":23,"Name":"售后服务部","ParentId":19,"SortNo":0,"ChildDepartments":[]}]},{"Id":24,"Name":"销售部","ParentId":1,"SortNo":0,"ChildDepartments":[{"Id":25,"Name":"销售一部","ParentId":24,"SortNo":0,"ChildDepartments":[]},{"Id":26,"Name":"销售二部","ParentId":24,"SortNo":0,"ChildDepartments":[]}]},{"Id":73,"Name":"财务部","ParentId":1,"SortNo":0,"ChildDepartments":[]}]
     */

    private DepartmentBean Department;


    public DepartmentBean getDepartment() {
        return Department;
    }

    public void setDepartment(DepartmentBean Department) {
        this.Department = Department;
    }

    public static class DepartmentBean {
        private int Id;
        private String Name;
        private int ParentId;
        private int SortNo;
        /**
         * Id : 2
         * Name : 总经办
         * ParentId : 1
         * SortNo : 1
         * ChildDepartments : []
         */

        private List<DepartmentBean> ChildDepartments;

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public int getParentId() {
            return ParentId;
        }

        public void setParentId(int ParentId) {
            this.ParentId = ParentId;
        }

        public int getSortNo() {
            return SortNo;
        }

        public void setSortNo(int SortNo) {
            this.SortNo = SortNo;
        }

        public List<DepartmentBean> getChildDepartments() {
            return ChildDepartments;
        }

        public void setChildDepartments(List<DepartmentBean> ChildDepartments) {
            this.ChildDepartments = ChildDepartments;
        }


    }
}
