package io.ggammu.realspringbootjpa.repository;

import io.ggammu.realspringbootjpa.domain.Member;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {

    @PersistenceContext // entityManger 주입
    private EntityManager em;

    @PersistenceUnit // entityMangerFacgtory 주입
    private EntityManagerFactory entityManagerFactory;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }

}
