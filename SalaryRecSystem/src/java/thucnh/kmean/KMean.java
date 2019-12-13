/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thucnh.kmean;

import java.util.List;
import thucnh.dao.ClusterDao;
import thucnh.dao.JobDao;
import thucnh.entity.TblCluster;
import thucnh.entity.TblJob;
import thucnh.utils.AppHelper;

/**
 *
 * @author HP
 */
public class KMean {

    private int skillId;
    private String expLevel;
    JobDao jobDao = JobDao.getInstance();

    List<TblJob> data;
    private double max;
    private double min;

    public KMean(int skillId, String expLevel, List<TblJob> data, double min, double max) {
        this.skillId = skillId;
        this.expLevel = expLevel;
        this.data = data;
        this.max = max;
        this.min = min;
    }

    public void init() {
//        System.out.println("----------------------------------------------------------------");
//        System.out.println("SKILL : " + skillId + "EXP-LEVEL : " + expLevel);
//        System.out.println("MIN : " + min + "   ----   MAX : " + max);

        if (data.size() < 5) {
            TblCluster cluster = new TblCluster();
            int hasValue = AppHelper.hasingString(expLevel + skillId);
            cluster.setHash(hasValue);
            cluster.setCentroid(AppHelper.getBeautyNumber(data.get(0).getSalary()));
            ClusterDao clusterDao = ClusterDao.getInstance();
            TblCluster entity = clusterDao.create(cluster);
            for (TblJob tblJob : data) {
                tblJob.setClusterId(entity);
                jobDao.update(tblJob);
            }
        } else {
            int noOfClusters = 3;
            Double centroid[][] = new Double[][]{
                {0.0, 0.0, 0.0},
                {min, (min + max) / 2, max}
            };
            if (data != null && data.size() > 0) {
                List<TblJob> result = getCentroid(data, noOfClusters, centroid);
                if (result != null) {
                    for (TblJob tblJob : result) {
                        jobDao.update(tblJob);
                    }
                }
            }
        }

    }

    public List<TblJob> getCentroid(List<TblJob> data, int noOfClusters, Double centroid[][]) {
        Double distance[][] = new Double[noOfClusters][data.size()];
        int indexClusterHasMinDistance[] = new int[data.size()];
        int clusterNodeCount[] = new int[noOfClusters];

        // đệ quy lại để set centroids mới lấy đc vào thành centroid mới
        centroid[0] = centroid[1];
        centroid[1] = new Double[]{0.0, 0.0, 0.0};

        for (int i = 0; i < noOfClusters; i++) {
            for (int j = 0; j < data.size(); j++) {
                distance[i][j] = Math.abs(data.get(j).getSalary() - centroid[0][i]);

            }
        }

        for (int j = 0; j < data.size(); j++) {
            int smallerDistance = 0;
            if (distance[0][j] < distance[1][j] && distance[0][j] < distance[2][j]) {
                smallerDistance = 0;
            }
            if (distance[1][j] < distance[0][j] && distance[1][j] < distance[2][j]) {
                smallerDistance = 1;
            }
            if (distance[2][j] < distance[0][j] && distance[2][j] < distance[1][j]) {
                smallerDistance = 2;//
            }
//            centroid[1][smallerDistance] Tổng của các node khi tạo thành cluster temp mới
            centroid[1][smallerDistance] = centroid[1][smallerDistance] + data.get(j).getSalary();
//            Tổng số lượng node
//              Từ tổng của các node trong cluster / tổng số lượng node -> ra centroid mới
            clusterNodeCount[smallerDistance] = clusterNodeCount[smallerDistance] + 1;
            indexClusterHasMinDistance[j] = smallerDistance;

        }



        // Set lại centroids mới ở trên vào centroid cũ
        for (int j = 0; j < noOfClusters; j++) {
            centroid[1][j] = centroid[1][j] / clusterNodeCount[j];
            Double value = centroid[1][j];
            if (value.isNaN()) {
                TblCluster cluster = new TblCluster();
                int hasValue = AppHelper.hasingString(expLevel + skillId);
                cluster.setHash(hasValue);
                cluster.setCentroid(AppHelper.getBeautyNumber(data.get(0).getSalary()));
                ClusterDao clusterDao = ClusterDao.getInstance();
                TblCluster entity = clusterDao.create(cluster);
                for (TblJob tblJob : data) {
                    tblJob.setClusterId(entity);
                    jobDao.update(tblJob);
                }
                return null;
            }
        }
//
        boolean isAchived = true;
        for (int j = 0; j < noOfClusters; j++) {
            if (isAchived && (percent(centroid[0][j], centroid[1][j]) < 40.0)) {
                isAchived = true;
                continue;
            }
            isAchived = false;
        }

        if (!isAchived) {

            getCentroid(data, noOfClusters, centroid);
        }
//
        if (isAchived) {
            for (int i = 0; i < noOfClusters; i++) {
                TblCluster cluster = new TblCluster();
                int hasValue = AppHelper.hasingString(expLevel + skillId);
                cluster.setHash(hasValue);
                cluster.setCentroid(AppHelper.getBeautyNumber(centroid[1][i]));
                ClusterDao dao = ClusterDao.getInstance();
                TblCluster entity = dao.create(cluster);
                if (entity != null) {
                    for (int j = 0; j < data.size(); j++) {
                        if (indexClusterHasMinDistance[j] == i) {
                            data.get(j).setClusterId(entity);
                        }
                    }
                }

//                System.out.println();
            }
        }

//        return centroid;
        return data;
    }

    static Double percent(Double a, Double b) {
        Double result = 0.0;
        if (b > a) {
            result = ((b - a) * 100) / a;
        } else {
            result = ((a - b) * 100) / a;
        }

        return result;
    }
}
