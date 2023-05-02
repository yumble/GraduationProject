package project.graduation.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import project.graduation.dto.CollectResponseDto;
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
    public Page<CollectResponseDto> findAllByAddressId(String addressId, Pageable pageable){
        List<Collect> content = queryFactory
                .select(collect)
                .from(collect)
                .join(collect.generalFile, generalFile)
                .join(collect.gps, gPS)
                .leftJoin(collect.program, program)
                .join(collect.floor, floor1)
                .fetchJoin()
                .where(floor1.address.addressId.eq(addressId))
                .orderBy(collect.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(collect)
                .from(collect)
                .join(collect.floor, floor1)
                .fetchJoin()
                .where(floor1.address.addressId.eq(addressId))
                .fetch().size();

        return new PageImpl<>(content.stream().map(CollectResponseDto::new).collect(Collectors.toList()), pageable, total);
    }
    @Override
    public CollectResponseDto findByCollectId(UUID collectId){
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

        return new CollectResponseDto(content);
    }
}
