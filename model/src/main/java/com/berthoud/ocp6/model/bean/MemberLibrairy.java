package com.berthoud.ocp6.model.bean;

public class MemberLibrairy {

    private int memberId;
    private int guidebookId;
    private boolean loanAvailable;

    public MemberLibrairy(int memberId, int guidebookId, boolean loanAvailable) {
        this.memberId = memberId;
        this.guidebookId = guidebookId;
        this.loanAvailable = loanAvailable;
    }

    public MemberLibrairy() {
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getGuidebookId() {
        return guidebookId;
    }

    public void setGuidebookId(int guidebookId) {
        this.guidebookId = guidebookId;
    }

    public boolean isLoanAvailable() {
        return loanAvailable;
    }

    public void setLoanAvailable(boolean loanAvailable) {
        this.loanAvailable = loanAvailable;
    }

    @Override
    public String toString() {
        return "MemberLibrairy{" +
                "memberId=" + memberId +
                ", guidebookId=" + guidebookId +
                ", loanAvailable=" + loanAvailable +
                '}';
    }
}
