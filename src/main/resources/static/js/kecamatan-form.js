(() => {
    document.addEventListener('DOMContentLoaded', function() {
        let mainUrl = "http://localhost:8082/api/kecamatan";
        let isLoading = false;

        // Fungsi untuk mengambil suggestion
        function fetchSuggestions(query = '', target, parentValue) {
            if (isLoading) return;
            isLoading = true;

            let apiUrl = `${target}-options`;
            let url = `${mainUrl}/${apiUrl}`;

            if (parentValue) {
                // Jika ada parentValue, kirim sebagai parameter
                url += `?parent=${parentValue}`;
            }

            // Menampilkan indikator loading
            const suggestionsContainer = document.querySelector(`.suggestions.${target}`);
            suggestionsContainer.innerHTML = '<div class="loading-spinner">Memuat...</div>';  // Menambahkan spinner atau pesan loading

            fetch(url)
                .then(response => response.json())
                .then(data => {
                    isLoading = false;
                    let filteredData = [];

                    // Filter data berdasarkan query
                    filteredData = query
                        ? data.filter(item => item.name.toLowerCase().includes(query.toLowerCase()))
                        : data;

                    suggestionsContainer.innerHTML = '';  // Mengosongkan loading message

                    if (filteredData.length > 0) {
                        // Tampilkan suggestion
                        filteredData.forEach(item => {
                            const div = document.createElement('div');
                            div.classList.add('suggestion-item');
                            div.textContent = item.name;
                            div.setAttribute('data-id', item.id); // Menyimpan ID di data-id

                            // Ketika item dipilih
                            div.addEventListener('click', function () {
                                const inputField = document.querySelector(`input#${target}`);
                                inputField.value = item.name; // Set input field dengan nama

                                // Simpan ID yang dipilih dalam atribut data-id
                                inputField.setAttribute('data-id', item.id);

                                // Kosongkan suggestions setelah memilih
                                suggestionsContainer.innerHTML = '';
                            });

                            suggestionsContainer.appendChild(div);
                        });
                    } else {
                        const noResultMessage = document.createElement('div');
                        noResultMessage.classList.add('suggestion-item');
                        noResultMessage.textContent = 'Tidak ada hasil ditemukan.';
                        suggestionsContainer.appendChild(noResultMessage);
                    }
                })
                .catch(error => {
                    console.error('Terjadi kesalahan saat mengambil data:', error);
                    suggestionsContainer.innerHTML = '<div class="suggestion-item">Terjadi kesalahan saat memuat data.</div>';
                    isLoading = false;
                });
        }

        // Menangani event input pada semua input field
        document.querySelectorAll('input').forEach(inputField => {
            inputField.addEventListener('input', () => {
                const query = inputField.value;
                const target = inputField.classList[0].replace('search-', '');
                let parentValue = '';

                // Jika target adalah kabupaten, ambil ID provinsi
                if (target === 'kabupaten') {
                    parentValue = document.querySelector('#provinsi').getAttribute('data-id');
                }

                fetchSuggestions(query, target, parentValue); // Kirimkan parentValue untuk filtering
            });
        });

        // Menangani event klik pada icon search
        document.querySelectorAll('.search-icon').forEach(searchIcon => {
            searchIcon.addEventListener('click', () => {
                const target = searchIcon.getAttribute('data-target');
                const query = document.querySelector(`input#${target}`).value;
                let parentValue = '';

                // Jika target adalah kabupaten, ambil ID provinsi
                if (target === 'kabupaten') {
                    parentValue = document.querySelector('#provinsi').getAttribute('data-id');
                }

                fetchSuggestions(query, target, parentValue); // Kirimkan parentValue
            });
        });

        // Menutup suggestions ketika klik di luar search container
        document.addEventListener('click', function(event) {
            const isClickInside = event.target.closest('.search-container');
            if (!isClickInside) {
                document.querySelectorAll('.suggestions').forEach(container => {
                    container.innerHTML = '';
                });
            }
        });
    });
})();
