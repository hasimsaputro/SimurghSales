(() => {
    document.addEventListener('DOMContentLoaded', function() {
        let mainUrl = "http://localhost:8082/api/kelurahan";
        let isLoading = false;

        function fetchSuggestions(query = '', target, parentValue) {
            if (isLoading) return;
            isLoading = true;

            let apiUrl = `${target}-options`;
            let url = `${mainUrl}/${apiUrl}`;

            if (parentValue) {
                url += `?parent=${parentValue}`;
            }

            const suggestionsContainer = document.querySelector(`.suggestions.${target}`);
            suggestionsContainer.innerHTML = '<div class="loading-spinner">Memuat...</div>';

            fetch(url)
                .then(response => response.json())
                .then(data => {
                    isLoading = false;
                    let filteredData = [];

                    filteredData = query
                        ? data.filter(item => item.name.toLowerCase().includes(query.toLowerCase()))
                        : data;

                    suggestionsContainer.innerHTML = '';

                    if (filteredData.length > 0) {
                        filteredData.forEach(item => {
                            const div = document.createElement('div');
                            div.classList.add('suggestion-item');
                            div.textContent = `${item.id} - ${item.name}`;
                            div.setAttribute('data-id', item.id);

                            div.addEventListener('click', function () {
                                const inputField = document.querySelector(`input#${target}`);
                                inputField.value = `${item.id} - ${item.name}`;

                                inputField.setAttribute('data-id', item.id);

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

        document.querySelectorAll('input').forEach(inputField => {
            inputField.addEventListener('input', () => {
                const query = inputField.value;
                const target = inputField.classList[0].replace('search-', '');
                let parentValue = '';

                if (target === 'kabupaten') {
                    parentValue = document.querySelector('#provinsi').getAttribute('data-id');
                } else if (target === 'kecamatan') {
                    parentValue = document.querySelector('#kabupaten').getAttribute('data-id');
                }

                fetchSuggestions(query, target, parentValue);
            });
        });

        document.querySelectorAll('.search-icon').forEach(searchIcon => {
            searchIcon.addEventListener('click', () => {
                const target = searchIcon.getAttribute('data-target');
                const query = document.querySelector(`input#${target}`).value;
                let parentValue = '';

                if (target === 'kabupaten') {
                    parentValue = document.querySelector('#provinsi').getAttribute('data-id');
                } else if (target === 'kecamatan') {
                    parentValue = document.querySelector('#kabupaten').getAttribute('data-id');
                }

                fetchSuggestions(query, target, parentValue);
            });
        });

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
