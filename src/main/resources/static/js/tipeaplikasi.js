let urlTipeAplikasiMaster = 'http://localhost:8082/api/tipeAplikasi/getSearchItems/'; // URL yang mengarah ke controller baru
let searchInputTipeAplikasi = document.querySelector('input.searchTipeAplikasi');
let suggestionContainerTipeAplikasi = document.querySelector('.search-container-filter .suggestions');
let selectFilterTipeAplikasi = document.querySelector('.filter select'); // Elemen select filter
suggestionContainerTipeAplikasi.style.display = 'none';

if (searchInputTipeAplikasi) {
    searchInputTipeAplikasi.addEventListener('input', function () {
        const query = searchInputTipeAplikasi.value.toLowerCase(); // Mengambil input yang dimasukkan oleh pengguna
        const filterValue = selectFilterTipeAplikasi.value; // Mengambil nilai filter yang dipilih
        suggestionContainerTipeAplikasi.innerHTML = ''; // Menghapus daftar suggestion sebelumnya

        // Menyembunyikan suggestion jika input kosong
        if (!query) {
            suggestionContainerTipeAplikasi.style.display = 'none';
            return; // Tidak melanjutkan proses pencarian jika input kosong
        }

        // Menampilkan suggestion container jika ada input
        suggestionContainerTipeAplikasi.style.display = 'block';

        // Melakukan request ke API dengan menambahkan filter sebagai bagian dari URL
        fetch(`${urlTipeAplikasiMaster}${filterValue}`)
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
                        searchInputTipeAplikasi.value = item; // Mengisi input dengan item yang dipilih
                        suggestionContainerTipeAplikasi.innerHTML = ''; // Menghapus suggestion setelah dipilih
                        suggestionContainerTipeAplikasi.style.display = 'none'; // Menyembunyikan suggestion setelah dipilih
                    });

                    suggestionContainerTipeAplikasi.appendChild(div); // Menambahkan suggestion ke container
                });

                // Jika tidak ada hasil yang cocok, menyembunyikan suggestion
                if (filteredData.length === 0) {
                    suggestionContainerTipeAplikasi.innerHTML = '<div class="no-results">No results found</div>';
                }
            })
            .catch(error => {
                console.error('Error fetching data:', error); // Menangani error jika request gagal
            });
    });
}
