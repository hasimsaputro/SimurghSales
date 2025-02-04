let urlTipeAplikasi = 'http://localhost:8082/api/tipeAplikasi/getSearchItems/'; // URL yang mengarah ke controller baru
let searchInput = document.querySelector('input.searchTipeAplikasi');
let suggestionsContainer = document.querySelector('.search-container .suggestions');
let selectFilter = document.querySelector('.filter select'); // Elemen select filter
suggestionsContainer.style.display = 'none';

if (searchInput) {
    searchInput.addEventListener('input', function () {
        const query = searchInput.value.toLowerCase(); // Mengambil input yang dimasukkan oleh pengguna
        const filterValue = selectFilter.value; // Mengambil nilai filter yang dipilih
        suggestionsContainer.innerHTML = ''; // Menghapus daftar suggestion sebelumnya

        // Menyembunyikan suggestion jika input kosong
        if (!query) {
            suggestionsContainer.style.display = 'none';
            return; // Tidak melanjutkan proses pencarian jika input kosong
        }

        // Menampilkan suggestion container jika ada input
        suggestionsContainer.style.display = 'block';

        // Melakukan request ke API dengan menambahkan filter sebagai bagian dari URL
        fetch(`${urlTipeAplikasi}${filterValue}`)
            .then(response => response.json()) // Mengonversi response menjadi format JSON
            .then(data => {
                const items = data.map(option => option.text); // Asumsikan response data memiliki properti 'text'
                // Filter hasil berdasarkan query
                const filteredData = items.filter(item => item.toLowerCase().includes(query));

                filteredData.forEach(item => {
                    const div = document.createElement('div');
                    div.classList.add('suggestion-item');
                    div.textContent = item;

                    // Menambahkan event listener untuk memilih suggestion
                    div.addEventListener('click', function () {
                        searchInput.value = item; // Mengisi input dengan item yang dipilih
                        suggestionsContainer.innerHTML = ''; // Menghapus suggestion setelah dipilih
                        suggestionsContainer.style.display = 'none'; // Menyembunyikan suggestion setelah dipilih
                    });

                    suggestionsContainer.appendChild(div); // Menambahkan suggestion ke container
                });

                // Jika tidak ada hasil yang cocok, menyembunyikan suggestion
                if (filteredData.length === 0) {
                    suggestionsContainer.innerHTML = '<div class="no-results">No results found</div>';
                }
            })
            .catch(error => {
                console.error('Error fetching data:', error); // Menangani error jika request gagal
            });
    });
}
