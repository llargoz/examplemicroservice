package by.llargoz.paymentservice.mapper;

import by.llargoz.paymentservice.dto.PaymentDto;
import by.llargoz.paymentservice.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PaymentMapper {

    PaymentMapper PAYMENT_MAPPER = Mappers.getMapper(PaymentMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "timestamp", ignore = true)
    Payment toEntity(PaymentDto paymentDto);

}
