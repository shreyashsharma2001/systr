#include <stdio.h>
#include <omp.h>

int main() {
    int N = 10; // Total number of elements in the array
    int n = 5;  // Number of processors

    int arr[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}; // Input array

    int chunk = N / n; // Number of elements each processor will handle

    int sum = 0; // Final sum of all elements

for (int i = 0; i < 10; ++i)
    {
        printf("%d,",arr[i]) ;
    }
    printf("\n");
    
    #pragma omp parallel num_threads(n)
    {
        int thread_id = omp_get_thread_num();
        int start = thread_id * chunk;
        int end = start + chunk;
        int local_sum = 0; // Intermediate sum calculated by each processor
        

        // Calculate local sum
        for (int i = start; i < end; i++) {
            local_sum += arr[i];
        }

        printf("Thread %d: Intermediate sum = %d\n", thread_id, local_sum);

        #pragma omp critical
        {
            sum += local_sum; // Add local sum to the final sum
        }
    }

    printf("Final sum = %d\n", sum);

    return 0;
}
