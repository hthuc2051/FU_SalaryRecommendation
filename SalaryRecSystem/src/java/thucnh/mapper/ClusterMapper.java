/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thucnh.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import thucnh.dto.ClusterDto;
import thucnh.entity.TblCluster;

/**
 *
 * @author HP
 */
public class ClusterMapper extends XmlAdapter<ClusterDto, TblCluster> {


    public List<ClusterDto> toListDto(List<TblCluster> listEntity) {
        try {
            if (listEntity != null) {
                List<ClusterDto> result = new ArrayList<>();
                for (TblCluster entity : listEntity) {
                    ClusterDto dto = marshal(entity);
                    if (dto != null) {
                        result.add(dto);
                    }
                }
                return result;
            }
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public TblCluster unmarshal(ClusterDto v) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ClusterDto marshal(TblCluster entity) throws Exception {
        if (entity != null) {
            ClusterDto dto = new ClusterDto();
            dto.setId(entity.getId());
            dto.setCentroid(entity.getCentroid());
            dto.setHash(entity.getHash());
            return dto;
        }
        return null;
    }
}
