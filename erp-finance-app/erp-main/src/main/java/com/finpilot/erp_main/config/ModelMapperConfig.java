package com.finpilot.erp_main.config;
import com.finpilot.erp_ar.dto.InvoiceResponseDTO;
import com.finpilot.erp_ar.entity.Invoice;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration()
                .setAmbiguityIgnored(true) // 🔑 Important
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);


        modelMapper.typeMap(Invoice.class, InvoiceResponseDTO.class).addMappings(m -> {
            m.map(src -> src.getCustomer().getCustomerId(), InvoiceResponseDTO::setCustomerId);
            m.map(src -> src.getCustomer().getName(), InvoiceResponseDTO::setCustomerName);
        });

        return modelMapper;
    }
}
