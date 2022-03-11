package jpql;

import javax.persistence.*;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            // 프로젝션(SELECT)
            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            em.flush();
            em.clear();

            // 엔티티 프로젝션 1
//            List<Member> result = em.createQuery("select m from Member m", Member.class)
//                    .getResultList();
//
//            Member findMember = result.get(0);
//            findMember.setAge(20);

            // 엔티티 프로젝션 2
            /**
             * JPQL은 최대한 NativeSQL과 비슷하게 작성
             * JPQL을 한눈에 봤을 때 join을 하는지 명확히 알 수 없음
             */
//            List<Team> result = em.createQuery("select m.team from Member m", Team.class)
//                    .getResultList();

            /**
             * NativeSQL과 비슷한 JPQL 작성 방법
             * JPQL을 한눈에 봤을 때 join을 하는지 명확히 알 수 있음
             */
//            List<Team> result = em.createQuery("select t from Member m join m.team t", Team.class)
//                    .getResultList();

            // 임베디드 타입 프로젝션
            /**
             * 한계가 있다.
             * 임베디드 타입은 어디에 소속되어 있기 때문에 소속된 엔티티로부터 시작해야한다.
             */
//            em.createQuery("select o.address from Order o", Address.class)
//                    .getResultList();

            // 스칼라 타입 프로젝션
            /**
             * NativeSQL과 가장 비슷한 방법
             */
//            em.createQuery("select m.username, m.age from Member m")
//                    .getResultList();

            // 프로젝션 - 여러 값 조회, Object[] 타입으로 조회 1
//            List resultList = em.createQuery("select m.username, m.age from Member m")
//                    .getResultList();
//
//            Object o = resultList.get(0);
//            Object[] result = (Object[]) o;
//            System.out.println("username = " + result[0]);
//            System.out.println("age = " + result[1]);

            // 프로젝션 - 여러 값 조회, Object[] 타입으로 조회 2
            /**
             * 추천하지는 않음
             */
//            List<Object[]> resultList = em.createQuery("select m.username, m.age from Member m")
//                    .getResultList();
//            Object[] result = resultList.get(0);
//            System.out.println("username = " + result[0]);
//            System.out.println("age = " + result[1]);

            // 프로젝션 - 여러 값 조회, new 명령어로 조회
            /**
             * 제일 깔끔한 방법
             * 다만 패키지가 길어지면 쿼리가 길어진다.
             * queryDSL을 사용하게 되면 해결될 문제
             */
            List<MemberDTO> result = em.createQuery("select new jpql.MemberDTO(m.username, m.age) from Member m", MemberDTO.class)
                    .getResultList();
            MemberDTO memberDTO = result.get(0);
            System.out.println("memberDTO = " + memberDTO.getUsername());
            System.out.println("memberDTO = " + memberDTO.getAge());


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        emf.close();
    }

}
