package cn.zain.model.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RobotNerExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public RobotNerExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andRobotSnIsNull() {
            addCriterion("robot_sn is null");
            return (Criteria) this;
        }

        public Criteria andRobotSnIsNotNull() {
            addCriterion("robot_sn is not null");
            return (Criteria) this;
        }

        public Criteria andRobotSnEqualTo(String value) {
            addCriterion("robot_sn =", value, "robotSn");
            return (Criteria) this;
        }

        public Criteria andRobotSnNotEqualTo(String value) {
            addCriterion("robot_sn <>", value, "robotSn");
            return (Criteria) this;
        }

        public Criteria andRobotSnGreaterThan(String value) {
            addCriterion("robot_sn >", value, "robotSn");
            return (Criteria) this;
        }

        public Criteria andRobotSnGreaterThanOrEqualTo(String value) {
            addCriterion("robot_sn >=", value, "robotSn");
            return (Criteria) this;
        }

        public Criteria andRobotSnLessThan(String value) {
            addCriterion("robot_sn <", value, "robotSn");
            return (Criteria) this;
        }

        public Criteria andRobotSnLessThanOrEqualTo(String value) {
            addCriterion("robot_sn <=", value, "robotSn");
            return (Criteria) this;
        }

        public Criteria andRobotSnLike(String value) {
            addCriterion("robot_sn like", value, "robotSn");
            return (Criteria) this;
        }

        public Criteria andRobotSnNotLike(String value) {
            addCriterion("robot_sn not like", value, "robotSn");
            return (Criteria) this;
        }

        public Criteria andRobotSnIn(List<String> values) {
            addCriterion("robot_sn in", values, "robotSn");
            return (Criteria) this;
        }

        public Criteria andRobotSnNotIn(List<String> values) {
            addCriterion("robot_sn not in", values, "robotSn");
            return (Criteria) this;
        }

        public Criteria andRobotSnBetween(String value1, String value2) {
            addCriterion("robot_sn between", value1, value2, "robotSn");
            return (Criteria) this;
        }

        public Criteria andRobotSnNotBetween(String value1, String value2) {
            addCriterion("robot_sn not between", value1, value2, "robotSn");
            return (Criteria) this;
        }

        public Criteria andNerKeyIsNull() {
            addCriterion("ner_key is null");
            return (Criteria) this;
        }

        public Criteria andNerKeyIsNotNull() {
            addCriterion("ner_key is not null");
            return (Criteria) this;
        }

        public Criteria andNerKeyEqualTo(String value) {
            addCriterion("ner_key =", value, "nerKey");
            return (Criteria) this;
        }

        public Criteria andNerKeyNotEqualTo(String value) {
            addCriterion("ner_key <>", value, "nerKey");
            return (Criteria) this;
        }

        public Criteria andNerKeyGreaterThan(String value) {
            addCriterion("ner_key >", value, "nerKey");
            return (Criteria) this;
        }

        public Criteria andNerKeyGreaterThanOrEqualTo(String value) {
            addCriterion("ner_key >=", value, "nerKey");
            return (Criteria) this;
        }

        public Criteria andNerKeyLessThan(String value) {
            addCriterion("ner_key <", value, "nerKey");
            return (Criteria) this;
        }

        public Criteria andNerKeyLessThanOrEqualTo(String value) {
            addCriterion("ner_key <=", value, "nerKey");
            return (Criteria) this;
        }

        public Criteria andNerKeyLike(String value) {
            addCriterion("ner_key like", value, "nerKey");
            return (Criteria) this;
        }

        public Criteria andNerKeyNotLike(String value) {
            addCriterion("ner_key not like", value, "nerKey");
            return (Criteria) this;
        }

        public Criteria andNerKeyIn(List<String> values) {
            addCriterion("ner_key in", values, "nerKey");
            return (Criteria) this;
        }

        public Criteria andNerKeyNotIn(List<String> values) {
            addCriterion("ner_key not in", values, "nerKey");
            return (Criteria) this;
        }

        public Criteria andNerKeyBetween(String value1, String value2) {
            addCriterion("ner_key between", value1, value2, "nerKey");
            return (Criteria) this;
        }

        public Criteria andNerKeyNotBetween(String value1, String value2) {
            addCriterion("ner_key not between", value1, value2, "nerKey");
            return (Criteria) this;
        }

        public Criteria andNerValueIsNull() {
            addCriterion("ner_value is null");
            return (Criteria) this;
        }

        public Criteria andNerValueIsNotNull() {
            addCriterion("ner_value is not null");
            return (Criteria) this;
        }

        public Criteria andNerValueEqualTo(String value) {
            addCriterion("ner_value =", value, "nerValue");
            return (Criteria) this;
        }

        public Criteria andNerValueNotEqualTo(String value) {
            addCriterion("ner_value <>", value, "nerValue");
            return (Criteria) this;
        }

        public Criteria andNerValueGreaterThan(String value) {
            addCriterion("ner_value >", value, "nerValue");
            return (Criteria) this;
        }

        public Criteria andNerValueGreaterThanOrEqualTo(String value) {
            addCriterion("ner_value >=", value, "nerValue");
            return (Criteria) this;
        }

        public Criteria andNerValueLessThan(String value) {
            addCriterion("ner_value <", value, "nerValue");
            return (Criteria) this;
        }

        public Criteria andNerValueLessThanOrEqualTo(String value) {
            addCriterion("ner_value <=", value, "nerValue");
            return (Criteria) this;
        }

        public Criteria andNerValueLike(String value) {
            addCriterion("ner_value like", value, "nerValue");
            return (Criteria) this;
        }

        public Criteria andNerValueNotLike(String value) {
            addCriterion("ner_value not like", value, "nerValue");
            return (Criteria) this;
        }

        public Criteria andNerValueIn(List<String> values) {
            addCriterion("ner_value in", values, "nerValue");
            return (Criteria) this;
        }

        public Criteria andNerValueNotIn(List<String> values) {
            addCriterion("ner_value not in", values, "nerValue");
            return (Criteria) this;
        }

        public Criteria andNerValueBetween(String value1, String value2) {
            addCriterion("ner_value between", value1, value2, "nerValue");
            return (Criteria) this;
        }

        public Criteria andNerValueNotBetween(String value1, String value2) {
            addCriterion("ner_value not between", value1, value2, "nerValue");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andIsValidIsNull() {
            addCriterion("is_valid is null");
            return (Criteria) this;
        }

        public Criteria andIsValidIsNotNull() {
            addCriterion("is_valid is not null");
            return (Criteria) this;
        }

        public Criteria andIsValidEqualTo(String value) {
            addCriterion("is_valid =", value, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidNotEqualTo(String value) {
            addCriterion("is_valid <>", value, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidGreaterThan(String value) {
            addCriterion("is_valid >", value, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidGreaterThanOrEqualTo(String value) {
            addCriterion("is_valid >=", value, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidLessThan(String value) {
            addCriterion("is_valid <", value, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidLessThanOrEqualTo(String value) {
            addCriterion("is_valid <=", value, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidLike(String value) {
            addCriterion("is_valid like", value, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidNotLike(String value) {
            addCriterion("is_valid not like", value, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidIn(List<String> values) {
            addCriterion("is_valid in", values, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidNotIn(List<String> values) {
            addCriterion("is_valid not in", values, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidBetween(String value1, String value2) {
            addCriterion("is_valid between", value1, value2, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidNotBetween(String value1, String value2) {
            addCriterion("is_valid not between", value1, value2, "isValid");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}