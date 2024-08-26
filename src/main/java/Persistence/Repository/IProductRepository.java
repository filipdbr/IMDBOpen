package Persistence.Repository;



import Entity.ProductEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends IGenericRepository<ProductEntity, Long> {
    // Add any custom queries if needed
}