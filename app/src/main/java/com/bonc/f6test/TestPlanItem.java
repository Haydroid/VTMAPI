package com.bonc.f6test;

public class TestPlanItem {
    private String mName;
    private TestPlanKind mKind;

    public TestPlanItem(TestPlanKind kind, String name) {
        mName = name;
        mKind = kind;
    }

    public TestPlanKind getKind() {
        return mKind;
    }

    @Override
    public String toString() {
        return mName;
    }
}
