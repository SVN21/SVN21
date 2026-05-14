#include <stdio.h>
#include "mpi.h"

int main(int argc, char* argv[])
{
    int rank, size;
    int num = 10;

    MPI_Init(&argc, &argv);
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);
    MPI_Comm_size(MPI_COMM_WORLD, &size);

    if(rank == 0) {
        printf("Sending message containing: %d from rank %d\n", num, rank);
        MPI_Send(&num, 1, MPI_INT, 1, 1, MPI_COMM_WORLD);
    } else if(rank == 1) {
        printf("At rank %d\n", rank);
        MPI_Recv(&num, 1, MPI_INT, 0, 1, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
        printf("Received message containing: %d at rank %d\n", num, rank);
    }

    printf("Hello, world, I am %d of %d\n", rank, size);

    MPI_Finalize();
    return 0;
}

