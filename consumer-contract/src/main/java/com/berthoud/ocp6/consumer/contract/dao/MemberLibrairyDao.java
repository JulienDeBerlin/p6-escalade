package com.berthoud.ocp6.consumer.contract.dao;


import com.berthoud.ocp6.model.bean.MemberLibrairy;

import java.util.List;

public interface MemberLibrairyDao {

    public List<MemberLibrairy> findMemberLibrairyByGuidebookId(int GuidebookId);

}
