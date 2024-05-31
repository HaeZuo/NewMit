package com.haezuo.newmit.common.CommonDao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("commonDao")
public class CommonDao {

    @Autowired
    private SqlSessionTemplate sqlSession;

    public int selectListCnt(String queryId){
        return sqlSession.selectOne(queryId);
    }
    public int selectListCnt(String queryId, Object parameterObject){
        return sqlSession.selectOne(queryId, parameterObject);
    }

    public <E> List<E> selectList(String queryId){
        return sqlSession.selectList(queryId);
    }
    public <E> List<E> selectList(String queryId, Object parameterObject){
        return sqlSession.selectList(queryId, parameterObject);
    }

    public <T> T selectOne(String queryId) {
        return sqlSession.selectOne(queryId);
    }
    public <T> T selectOne(String queryId, int parameterInt){
        return sqlSession.selectOne(queryId, parameterInt);
    }

    public <T> T selectOne(String queryId, Object parameterObject){
        return sqlSession.selectOne(queryId, parameterObject);
    }

    public int insert(String queryId, Object parameterObject){
        return sqlSession.insert(queryId, parameterObject);
    }

    public String insertAndReturnKey(String queryId, Map<String, Object> parameterMap){
        sqlSession.insert(queryId, parameterMap);

        return parameterMap.get("id").toString();
    }

    public int update(String queryId, Object parameterObject){
        return sqlSession.update(queryId, parameterObject);
    }

    public int delete(String queryId, Object parameterObject){
        return sqlSession.delete(queryId, parameterObject);
    }

}