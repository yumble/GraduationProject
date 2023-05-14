package project.graduation.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import project.graduation.dto.CollectDetailDto;
import project.graduation.dto.CollectListDto;
import project.graduation.entity.Collect;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static project.graduation.entity.QAddress.address;
import static project.graduation.entity.QCollect.collect;
import static project.graduation.entity.QGeneralFile.generalFile;
import static project.graduation.entity.QGPS.gPS;
import static project.graduation.entity.QProgram.program;
import static project.graduation.entity.QFloor.floor1;

public class CollectRepositoryCustomImpl implements CollectRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public CollectRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }
    @Override
    public Page<CollectListDto> findAllByAddressId(String addressId, Pageable pageable){
        List<Collect> content = queryFactory
                .select(collect)
                .from(collect)
                .join(collect.generalFile, generalFile)
                .fetchJoin()
                .join(collect.gps, gPS)
                .fetchJoin()
                .leftJoin(collect.program, program)
                .fetchJoin()
                .join(collect.floor, floor1)
                .fetchJoin()
                .join(collect.floor.address, address)
                .fetchJoin()
                .where(addressIdEq(addressId))
                .orderBy(collect.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(collect)
                .from(collect)
                .join(collect.floor, floor1)
                .fetchJoin()
                .join(collect.floor.address, address)
                .fetchJoin()
                .where(addressIdEq(addressId))
                .fetch().size();

        return new PageImpl<>(content.stream().map(CollectListDto::new).collect(Collectors.toList()), pageable, total);
    }
    @Override
    public CollectDetailDto findByCollectId(UUID collectId){
        Collect content = queryFactory
                .select(collect)
                .from(collect)
                .join(collect.generalFile, generalFile)
                .fetchJoin()
                .join(collect.gps, gPS)
                .fetchJoin()
                .leftJoin(collect.program, program)
                .fetchJoin()
                .join(collect.floor, floor1)
                .fetchJoin()
                .join(collect.floor.address, address)
                .fetchJoin()
                .where(collect.collectId.eq(collectId))
                .fetchOne();

        return new CollectDetailDto(content);
    }
    private BooleanExpression addressIdEq(String addressId) {
        return addressId== null || addressId.equals(":") ? null :
                floor1.address.roadAddressName.eq(
                        JPAExpressions
                                .select(address.roadAddressName)
                                .from(address)
                                .where(address.addressId.eq(addressId)));
        //where a1_0.ROAD_ADDRESS_NAME = (select ROAD_ADDRESS_NAME from TB_ADDRESS_INFO where TB_ADDRESS_INFO.ADDRESS_ID = :addressId)
        //return addressId== null || addressId.equals(":") ? null : floor1.address.addressId.eq(addressId);
    }
}
