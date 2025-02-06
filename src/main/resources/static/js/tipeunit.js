let urlTipeUnitMaster = 'http://localhost:8082/api/tipeUnit/getSearchItems/'; // URL yang mengarah ke controller baru
let searchInputTipeUnit = document.querySelector('input.searchTipeUnit');
let suggestionContainerTipeUnit = document.querySelector('.search-container-filter .suggestions');
let selectFilterTipeUnit = document.querySelector('.filter select'); // Elemen select filter
suggestionContainerTipeUnit.style.display = 'none';

if (searchInputTipeUnit) {
    searchInputTipeUnit.addEventListener('input', function () {
        const query = searchInputTipeUnit.value.toLowerCase(); // Mengambil input yang dimasukkan oleh pengguna
        const filterValue = selectFilterTipeUnit.value; // Mengambil nilai filter yang dipilih
        suggestionContainerTipeUnit.innerHTML = ''; // Menghapus daftar suggestion sebelumnya

        // Menyembunyikan suggestion jika input kosong
        if (!query) {
            suggestionContainerTipeUnit.style.display = 'none';
            return; // Tidak melanjutkan proses pencarian jika input kosong
        }

        // Menampilkan suggestion container jika ada input
        suggestionContainerTipeUnit.style.display = 'block';

        // Melakukan request ke API dengan menambahkan filter sebagai bagian dari URL
        fetch(`${urlTipeUnitMaster}${filterValue}`)
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
                        searchInputTipeUnit.value = item; // Mengisi input dengan item yang dipilih
                        suggestionContainerTipeUnit.innerHTML = ''; // Menghapus suggestion setelah dipilih
                        suggestionContainerTipeUnit.style.display = 'none'; // Menyembunyikan suggestion setelah dipilih
                    });

                    suggestionContainerTipeUnit.appendChild(div); // Menambahkan suggestion ke container
                });

                // Jika tidak ada hasil yang cocok, menyembunyikan suggestion
                if (filteredData.length === 0) {
                    suggestionContainerTipeUnit.innerHTML = '<div class="no-results">No results found</div>';
                }
            })
            .catch(error => {
                console.error('Error fetching data:', error); // Menangani error jika request gagal
            });
    });
}
