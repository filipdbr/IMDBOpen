package Persistence.Repository;



import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends IGenericRepository<ProductEntity, Long> {
    // Add any custom queries if needed
}