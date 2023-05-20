package project.graduation.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import project.graduation.dto.FileGroupDto;
import project.graduation.entity.FileGroup;

import java.util.List;
import java.util.stream.Collectors;

import static project.graduation.entity.QAddress.address;
import static project.graduation.entity.QFileGroup.fileGroup;

public class FileGroupRepositoryCustomImpl implements FileGroupRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public FileGroupRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<FileGroupDto> findAllByAddress(String addressId, Pageable pageable) {
        List<FileGroup> content = queryFactory
                .select(fileGroup)
                .from(fileGroup)
                .join(fileGroup.collectList)
                .fetchJoin()
                .join(fileGroup.floor)
                .fetchJoin()
                .join(fileGroup.floor.address, address)
                .fetchJoin()
                .where(addressIdEq(addressId))
                .orderBy(fileGroup.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = content.size();

        return new PageImpl<>(content.stream().map(FileGroupDto::new).collect(Collectors.toList()), pageable, total);
    }

    private BooleanExpression addressIdEq(String addressId) {
        return addressId == null || addressId.equals(":") || addressId.equals(":addressId") ? null :
                address.roadAddressName.eq(
                        JPAExpressions
                                .select(address.roadAddressName)
                                .from(address)
                                .where(address.addressId.eq(addressId)));
    }
}
